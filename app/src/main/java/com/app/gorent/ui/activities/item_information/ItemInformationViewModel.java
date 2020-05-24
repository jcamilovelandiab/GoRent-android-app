package com.app.gorent.ui.activities.item_information;


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
import com.app.gorent.ui.activities.item_form.ItemFormState;
import com.app.gorent.utils.result.BasicResult;
import com.app.gorent.utils.result.CategoryListQueryResult;
import com.app.gorent.utils.result.ItemQueryResult;
import com.app.gorent.utils.Validator;

import java.util.List;

public class ItemInformationViewModel extends ViewModel {

    ItemRepository itemRepository;
    CategoryRepository categoryRepository;
    MutableLiveData<ItemFormState> itemFormState = new MutableLiveData<>();
    MutableLiveData<ItemQueryResult> itemQueryResult = new MutableLiveData<>();
    MutableLiveData<CategoryListQueryResult> categoryListQueryResult = new MutableLiveData<>();
    MutableLiveData<BasicResult> updateItemResult = new MutableLiveData<>();
    MutableLiveData<BasicResult> deleteItemResult = new MutableLiveData<>();

    public ItemInformationViewModel(ItemRepository itemRepository, CategoryRepository categoryRepository) {
        this.itemRepository = itemRepository;
        this.categoryRepository = categoryRepository;
        this.categoryRepository.getCategories(categoryListQueryResult);
    }

    public MutableLiveData<ItemFormState> getItemFormState() {
        return itemFormState;
    }

    public MutableLiveData<ItemQueryResult> getItemQueryResult() {
        return itemQueryResult;
    }

    public MutableLiveData<CategoryListQueryResult> getCategoryListQueryResult() {
        return categoryListQueryResult;
    }

    public MutableLiveData<BasicResult> getUpdateItemResult() {
        return updateItemResult;
    }

    public MutableLiveData<BasicResult> getDeleteItemResult() {
        return deleteItemResult;
    }

    public void findItemById(String id){
        itemRepository.getItemById(id, itemQueryResult);
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

    public void updateItem(String name, String description, String price, String feeType,
                            String nameCategory, String image_path){
        Category category = findCategoryByName(nameCategory);
        LoggedInUser loggedInUser = itemRepository.getLoggedInUser();
        ItemOwner itemOwner = new ItemOwner(loggedInUser.getEmail()+"", loggedInUser.getFull_name()+"");
        Item item = new Item(itemQueryResult.getValue().getItem().getId(), name+"", description+"",
                Long.parseLong(price), feeType+"", category, itemOwner, image_path+"");
        itemRepository.updateItem(item, updateItemResult);
    }

    public void deleteItem(){
        String itemId = itemQueryResult.getValue().getItem().getId();
        itemRepository.deleteItem(itemId, deleteItemResult);
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
