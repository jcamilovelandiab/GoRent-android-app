package com.app.gorent.ui.new_item;

import androidx.lifecycle.ViewModel;

import com.app.gorent.data.model.Category;
import com.app.gorent.data.model.Item;
import com.app.gorent.data.repositories.CategoryRepository;
import com.app.gorent.data.repositories.ItemRepository;
import com.app.gorent.utils.Result;

import java.util.List;

public class CreateItemFormViewModel extends ViewModel {

    ItemRepository itemRepository;
    CategoryRepository categoryRepository;

    public CreateItemFormViewModel(ItemRepository itemRepository, CategoryRepository categoryRepository) {
        this.itemRepository = itemRepository;
        this.categoryRepository = categoryRepository;
    }

    public void saveItem(Item item){
        Result result = itemRepository.saveItem(item);
    }

    public List<String> getCategories(){
        List<String> nameCategoryList =  categoryRepository.getNameCategories();
        return nameCategoryList;
    }

}
