package com.dontwait.shopapp.service.impl;

import com.dontwait.shopapp.dto.request.product.ProductCreationRequest;
import com.dontwait.shopapp.dto.request.product.ProductImageRequest;
import com.dontwait.shopapp.dto.request.product.ProductUpdateRequest;
import com.dontwait.shopapp.dto.response.ApiResponse;
import com.dontwait.shopapp.dto.response.ProductImageResponse;
import com.dontwait.shopapp.dto.response.ProductResponse;
import com.dontwait.shopapp.entity.*;
import com.dontwait.shopapp.enums.ErrorCode;
import com.dontwait.shopapp.exception.AppException;
import com.dontwait.shopapp.mapper.ProductImageMapper;
import com.dontwait.shopapp.mapper.ProductMapper;
import com.dontwait.shopapp.repository.CategoryRepository;
import com.dontwait.shopapp.repository.ProductImageRepository;
import com.dontwait.shopapp.repository.ProductRepository;
import com.dontwait.shopapp.service.ProductService;
import com.dontwait.shopapp.util.FileUtil;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    ProductMapper productMapper;
    ProductImageMapper productImageMapper;
    ProductRepository productRepository;
    ProductImageRepository productImageRepository;
    CategoryRepository categoryRepository;

    @Override
    public ProductResponse findProductById(Long productId) {
        Product existingProduct = productRepository.findById(productId)
                .filter(product -> product.getIsActive() == 1)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_ID_NOT_FOUND));
        return productMapper.toProductResponse(existingProduct);
    }

    @Override
    public List<ProductResponse> findAllProducts(Pageable pageable, String keyword, Long categoryId) {
        Specification<Product> spec = Specification.where(null);

        if(categoryId != null) {
            spec = spec.and((root, query, cb)
                    -> cb.equal(root.get("category")
                    .get("categoryId"), categoryId
                    )
            );
        }

        if (keyword != null && !keyword.isEmpty()) {
            spec = spec.and((root, query, cb) -> {
                List<Predicate> keyWordPredicates = new ArrayList<>();
                // filter trên trường của Order
                keyWordPredicates.add(cb.like(root.get("productName"), "%" + keyword + "%"));
                keyWordPredicates.add(cb.like(root.get("productDescription"), "%" + keyword + "%"));

                // join đến Category và Product để filter categoryName
                Join<Product, Category> categoryJoin = root.join("category", JoinType.LEFT);
                keyWordPredicates.add(cb.like(categoryJoin.get("categoryName"), "%" + keyword + "%"));

                // combine tất cả điều kiện bằng OR
                return cb.or(keyWordPredicates.toArray(new Predicate[0]));
            });
        }

        return productRepository.findAll(spec, pageable).stream()
                .map(productMapper::toProductResponse)
                .filter(productResponse -> productResponse.getIsActive() == 1)
                .toList();
    }

    @Override
    public ProductResponse createProduct(ProductCreationRequest request) throws IOException {
        Category existingCategory = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_ID_NOT_FOUND));
        Product product = productMapper.toProduct(request, existingCategory);

        return productMapper.toProductResponse(productRepository.save(product));
    }

    @Override
    public List<ProductImageResponse> uploadImages(Long productId, List<MultipartFile> files) throws IOException {
        files = files == null ? Collections.emptyList() : files;

        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_ID_NOT_FOUND));
        int currentSize = productImageRepository.findByProductProductId(productId).size();
        if(currentSize + files.size() > ProductImage.MAXIMUM_IMAGE_SIZE_PER_PRODUCT)
            throw new AppException(ErrorCode.SIZE_OF_PRODUCT_IMAGES_CANNOT_GREATER_THAN_5);

        //Validate before add to db
        //If invalid file, stop process
        for(MultipartFile file : files) {
            if(file.isEmpty())
                continue;
            if(file.getSize() > 10 * 1024 * 1024)
                throw new AppException(ErrorCode.FILE_TOO_LARGE);
            String contentType = file.getContentType();
            if(contentType == null || !contentType.startsWith("image/"))
                throw new AppException(ErrorCode.FILE_TYPE_NOT_SUPPORTED);
        }

        List<ProductImageResponse> newProductImagesResponse = new ArrayList<>();
        List<String> savedFiles = new ArrayList<>(); //Neu fail thi remove het hinh vua luu ra khoi local
        //Chi luu khi tat ca deu valid

        //Save to local and db
        try{
            for(MultipartFile file : files) {
                String filename = FileUtil.storeFile(file);
                savedFiles.add(filename);

                ProductImageRequest request = ProductImageRequest.builder()
                        .productId(productId)
                        .imageURL(filename)
                        .build();

                ProductImageResponse response = createProductImage(productId, request);
                newProductImagesResponse.add(response);
            }
            return newProductImagesResponse;
        }catch (Exception e) {
            for(String filename : savedFiles)
                FileUtil.deleteFile(filename);
            throw e;
        }
    }


    @Override
    public ProductImageResponse createProductImage(Long productId, ProductImageRequest request) {

        Product existingProduct = productRepository.findByProductId(productId)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_ID_NOT_FOUND));

        ProductImage newProductImage = productImageMapper.toProductImage(request, existingProduct);
        ;
        return productImageMapper.toProductImageResponse(productImageRepository.save(newProductImage));
    }

    @Override
    public Product getProductById(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_ID_NOT_FOUND));
    }

    @Override
    public void deleteProduct(Long productId) {
        Product existingProduct = productRepository.findByProductId(productId)
                        .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_ID_NOT_FOUND));
        existingProduct.setIsActive(0);
        productRepository.save(existingProduct);
    }

    @Override
    public ProductResponse updateProduct(Long productId, ProductUpdateRequest request) {
        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_ID_NOT_FOUND));

        Category existingCategory = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_ID_NOT_FOUND));

        productMapper.updateProduct(request, existingProduct, existingCategory);
        return productMapper.toProductResponse(productRepository.save(existingProduct));
    }
}
