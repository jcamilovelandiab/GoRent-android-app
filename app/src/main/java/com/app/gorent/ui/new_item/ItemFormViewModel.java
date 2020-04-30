package com.app.gorent.ui.new_item;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.app.gorent.R;
import com.app.gorent.data.model.Category;
import com.app.gorent.data.model.Item;
import com.app.gorent.data.repositories.CategoryRepository;
import com.app.gorent.data.repositories.ItemRepository;
import com.app.gorent.utils.BasicResult;
import com.app.gorent.utils.Result;
import com.app.gorent.utils.Validator;

import java.util.List;

public class ItemFormViewModel extends ViewModel {

    private MutableLiveData<ItemFormState> itemFormState = new MutableLiveData<>();
    private MutableLiveData<BasicResult> itemFormResult = new MutableLiveData<>();
    private ItemRepository itemRepository;
    private CategoryRepository categoryRepository;
    private Category category;

    public ItemFormViewModel(ItemRepository itemRepository, CategoryRepository categoryRepository) {
        this.itemRepository = itemRepository;
        this.categoryRepository = categoryRepository;
    }

    List<String> getCategories(){
        List<String> nameCategoryList =  categoryRepository.getNameCategories();
        return nameCategoryList;
    }

    public LiveData<ItemFormState> getItemFormState() {
        return itemFormState;
    }

    public LiveData<BasicResult> getItemFormResult() {
        return itemFormResult;
    }

    public void dataChanged(String name, String description, String price, String feeType, String category){
        if(!Validator.isItemNameValid(name)){
            itemFormState.setValue(new ItemFormState(R.string.invalid_item_name,
                    null, null,
                    null, null));
        }else if(!Validator.isDescriptionValid(description)){
            itemFormState.setValue(new ItemFormState(null,
                    R.string.invalid_item_description,
                    null, null, null));
        }else if(!Validator.isStringNumeric(price)){
            itemFormState.setValue(new ItemFormState(null,null,
                    R.string.invalid_item_price,
                    null, null));
        }else if(feeType.trim().isEmpty() || feeType.trim().equals("null")){
            itemFormState.setValue(new ItemFormState(null,null,null,
                    R.string.invalid_item_fee_type,
                    null));
        }else if(!isCategoryValid(category) || category.trim().equals("null")){
            itemFormState.setValue(new ItemFormState(null,null, null, null,
                    R.string.invalid_item_category));
        }else{
            itemFormState.setValue(new ItemFormState(true));
        }
    }

    private boolean isCategoryValid(String nameCategory){
        if(nameCategory.trim().isEmpty()){
            return false;
        }
        return true;
    }

    public void saveItem(String name, String description, String price, String feeType, String nameCategory){
        category = categoryRepository.getCategoryByName(nameCategory);
        Result result = itemRepository.saveItem(name+"", description+"", Long.parseLong(price), feeType+"", category);
        if(result instanceof Result.Success){
            itemFormResult.setValue(new BasicResult("Item was created successfully"));
        }else{
            itemFormResult.setValue(new BasicResult(R.string.item_creation_failed));
        }
    }


}
