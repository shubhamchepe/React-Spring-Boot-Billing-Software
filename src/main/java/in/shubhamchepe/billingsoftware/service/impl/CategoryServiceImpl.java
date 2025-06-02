package in.shubhamchepe.billingsoftware.service.impl;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import in.shubhamchepe.billingsoftware.entity.CategoryEntity;
import in.shubhamchepe.billingsoftware.io.CategoryRequest;
import in.shubhamchepe.billingsoftware.io.CategoryResponse;
import in.shubhamchepe.billingsoftware.repository.CategoryRepository;
import in.shubhamchepe.billingsoftware.service.CategoryService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService{
	
	// we have called this object from Repository to access functions
	//essential for communicating with database like : save(),find(),findAll()
	private final CategoryRepository categoryRepository;

	@Override
	public CategoryResponse add(CategoryRequest request) {
		//we need to convert the object coming from client to map it into database
		// DataType     Variable  =  MethodToconvert(clientObject)
		CategoryEntity newCategory = convertToEntity(request);
		//ConvertedObject = Map to database and save
		// response from categoryRepository.save() will be stored in newCategory
		// basically, it is taking response object and sending back to client
		// So, newCategory is holding Java Object
		newCategory =  categoryRepository.save(newCategory);
		// now we need to convert Java Object into simplified JSON format
		return convertToResponse(newCategory);
	}

	//converts Java Object To JSON
	private CategoryResponse convertToResponse(CategoryEntity newCategory) {
		return CategoryResponse.builder()
				.categoryId(newCategory.getCategoryId())
				.name(newCategory.getName())
				.description(newCategory.getDescription())
				.bgColor(newCategory.getBgColor())
				.imgUrl(newCategory.getImgUrl())
				.createdAt(newCategory.getCreatedAt())
				.updatedAt(newCategory.getUpdatedAt())
				.build();
	}

	//We are using this method to convert CategoryRequest object into a CategoryEntity object
	//Using builder() method which maps data coming from client as an object into database
	//converts JSON object coming from client to Java Object
	private CategoryEntity convertToEntity(CategoryRequest request) {
		return CategoryEntity.builder()
				.categoryId(UUID.randomUUID().toString())
				.name(request.getName())
				.description(request.getDescription())
				.bgColor(request.getBgColor())
				.build();
	}

	@Override
	public List<CategoryResponse> read() {
		//categoryRepository.findAll() will return List<CategoryEntity>
		//.stream() will convert list into Java Stream
		//.map() will map categoryEntity as each item and convert into JSON format
		// .collect(Collectors.toList()) collects all CategoryResponse objects in a new list
		//List<CategoryResponse> and returns it
		return categoryRepository.findAll().stream()
		.map(categoryEntity -> convertToResponse(categoryEntity))
		.collect(Collectors.toList());
	}

	@Override
	public void delete(String categoryId) {
		CategoryEntity category = categoryRepository.findByCategoryId(categoryId)
		        .orElseThrow(() -> new IllegalArgumentException("Category not found: " + categoryId));
		    categoryRepository.delete(category);
	}


}
