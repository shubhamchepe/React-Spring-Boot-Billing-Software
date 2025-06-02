package in.shubhamchepe.billingsoftware.service;

import java.util.List;

import in.shubhamchepe.billingsoftware.io.CategoryRequest;
import in.shubhamchepe.billingsoftware.io.CategoryResponse;

public interface CategoryService {

	CategoryResponse add(CategoryRequest request);
	
	List<CategoryResponse> read();
	
	void delete(String categoryId);
}
