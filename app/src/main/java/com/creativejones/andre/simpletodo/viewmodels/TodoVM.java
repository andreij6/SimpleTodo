package com.creativejones.andre.simpletodo.viewmodels;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.creativejones.andre.simpletodo.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class TodoVM {

    private static final String TAG = TodoVM.class.getSimpleName();
    public static final String TODO_ITEMS_KEY = "todo_items";

    //region Properties
    Context mContext;
    ActivityMainBinding mBinding;
    ArrayList<String> items = new ArrayList<>();
    ArrayAdapter<String> mAdapter;
    //endregion

    //region Static Factories
    public static TodoVM createSetup(Context context, ActivityMainBinding binding){
        TodoVM result = new TodoVM(context, binding);
        result.initialize();
        return result;
    }

    public static TodoVM recreate(Context context, ActivityMainBinding binding, Bundle savedInstanceState) {
        TodoVM result = TodoVM.createSetup(context, binding);
        addTodoItems(result, savedInstanceState);
        return result;
    }
    //endregion

    //region Constructor
    public TodoVM(Context context, ActivityMainBinding binding){
        mContext = context;
        mBinding = binding;
    }
    //endregion

    //region Public methods
    @SuppressWarnings("unused")
    /**
     * <p>This method returns onClickLister for the task item.  Inside the
     * onClick method the code grabs the user input validates it and adds the new item to the
     *  task list</p>
     * @return the Click Listener for the Task Item submit button
     */
    public View.OnClickListener submit(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String todoItem = mBinding.mainEtIteminput.getText().toString();

                if(isTaskInputValid(todoItem)) {
                    addItemToAdapterResetInput(todoItem);
                } else {
                    informOfInvalidInput();
                }
            }
        };
    }

    public void saveInstanceState(Bundle outState) {
        outState.putStringArrayList(TODO_ITEMS_KEY, items);
    }
    //endregion

    //region Helpers
    private void setItems(ArrayList<String> _items) {
        items = _items;
        mAdapter.addAll(items);
    }

    private boolean isTaskInputValid(String userInput){
        return !userInput.isEmpty();
    }

    private void informOfInvalidInput() {
        Toast.makeText(mContext, "Please enter a valid task", Toast.LENGTH_LONG).show();
    }

    private void addItemToAdapterResetInput(String todoItem) {
        items.add(todoItem);
        mAdapter.notifyDataSetChanged();

        mBinding.mainEtIteminput.setText("");
    }

    private static void addTodoItems(TodoVM result, Bundle savedInstanceState) {
        if(savedInstanceState.containsKey(TODO_ITEMS_KEY)){
            result.setItems(savedInstanceState.getStringArrayList(TODO_ITEMS_KEY));
        }
    }

    private void initialize() {
        mAdapter = new ArrayAdapter<>(mContext, android.R.layout.simple_list_item_1, items);

        mBinding.listView2.setAdapter(mAdapter);
    }
    //endregion
}
