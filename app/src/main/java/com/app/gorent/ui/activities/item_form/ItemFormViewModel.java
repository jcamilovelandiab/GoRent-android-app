package com.app.gorent.ui.activities.item_form;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.app.gorent.R;
import com.app.gorent.data.model.Category;
import com.app.gorent.data.model.Item;
import com.app.gorent.data.model.ItemOwner;
import com.app.gorent.data.model.LoggedInUser;
import com.app.gorent.data.repositories.CategoryRepository;
import com.app.gorent.data.repositories.ItemRepository;
import com.app.gorent.data.storage.Session;
import com.app.gorent.utils.BasicResult;
import com.app.gorent.utils.CategoryListQueryResult;
import com.app.gorent.utils.Validator;

import java.util.List;

public class ItemFormViewModel extends ViewModel {

    private MutableLiveData<ItemFormState> itemFormState = new MutableLiveData<>();
    private MutableLiveData<BasicResult> itemFormResult = new MutableLiveData<>();
    private MutableLiveData<CategoryListQueryResult> categoryListQueryResult = new MutableLiveData<>();

    private ItemRepository itemRepository;
    private CategoryRepository categoryRepository;

    public ItemFormViewModel(ItemRepository itemRepository, CategoryRepository categoryRepository) {
        this.itemRepository = itemRepository;
        this.categoryRepository = categoryRepository;
        this.categoryRepository.getCategories(categoryListQueryResult);
    }

    public LiveData<ItemFormState> getItemFormState() {
        return itemFormState;
    }

    public LiveData<BasicResult> getItemFormResult() {
        return itemFormResult;
    }

    public MutableLiveData<CategoryListQueryResult> getCategoryListQueryResult() {
        return categoryListQueryResult;
    }

    public void dataChanged(String name, String description, String price, String feeType, String category){
        if(!Validator.isItemNameValid(name)){
            itemFormState.setValue(new ItemFormState(R.string.invalid_item_name,
                    null, null,
                    null, null));
        }else if(!Validator.isStringValid(description)){
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

    public void saveItem(String name, String description, String price, String feeType, String nameCategory, String path_image){
        Category category = findCategoryByName(nameCategory);
        LoggedInUser loggedInUser = Session.getLoggedInUser();
        ItemOwner itemOwner = new ItemOwner(loggedInUser.getEmail()+"",loggedInUser.getFull_name()+"");
        Item item = new Item(name+"", description+"",
                Long.parseLong(price),feeType+"", category, itemOwner, path_image);
        itemRepository.saveItem(item, itemFormResult);
    }

    private Category findCategoryByName(String name){
        assert(categoryListQueryResult.getValue()!=null);
        List<Category> categories = categoryListQueryResult.getValue().getCategoryList();
        assert categories != null;
        for(Category c: categories){
            if(c.getName().equals(name)){
                return c;
            }
        }
        return null;
    }

}
