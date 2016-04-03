package com.creativejones.andre.simpletodo.viewmodels;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.creativejones.andre.simpletodo.data.TaskRepository;
import com.creativejones.andre.simpletodo.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class TodoVM {

    private static final String TAG = TodoVM.class.getSimpleName();

    public static final String TODO_ITEMS_KEY = "todo_items";

    //region Properties & Components
    Context mContext;
    ActivityMainBinding mBinding;
    ArrayList<String> items = new ArrayList<>();
    ArrayAdapter<String> mAdapter;
    TaskRepository mRepo;
    //endregion

    //region Static Factories
    public static TodoVM createSetup(Context context, ActivityMainBinding binding, Bundle savedInstanceState){
        TodoVM result = new TodoVM(context, binding);


        if(savedInstanceState != null){
            //pull items from bundle
            restoreTodoItems(result, savedInstanceState);
        } else {
            //read from data storage
            result.readSavedItems();

        }

        return result.build();
    }
    //endregion

    //region Constructor
    public TodoVM(Context context, ActivityMainBinding binding){
        mContext = context;
        mBinding = binding;
        mRepo = new TaskRepository(context);
    }
    //endregion

    //region Public methods
    @SuppressWarnings("unused")
    /**
     * <p>This method returns onClickLister for the task item.  Inside the
     * onClick method the code grabs the user input validates it and adds the new item to the
     *  task list</p>
     * @return the Click Listener for the Task Item addNewTodoItem button
     */
    public View.OnClickListener addNewTodoItem(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String todoItem = mBinding.mainEtIteminput.getText().toString();

                if(isTaskInputValid(todoItem)) {
                    completeAddTodoItem(todoItem);
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
        items.addAll(_items);
    }

    private boolean isTaskInputValid(String userInput){
        return !userInput.isEmpty();
    }

    private void informOfInvalidInput() {
        Toast.makeText(mContext, "Please enter a valid task", Toast.LENGTH_LONG).show();
    }

    private void completeAddTodoItem(String todoItem) {
        items.add(todoItem);
        mAdapter.notifyDataSetChanged();

        mBinding.mainEtIteminput.setText("");

        persistItems();
    }

    private void persistItems() {
        mRepo.add(items);
    }

    private void readSavedItems() {
        items = (ArrayList<String>)mRepo.getAll();
    }

    private static void restoreTodoItems(TodoVM result, Bundle savedInstanceState) {
        if(savedInstanceState.containsKey(TODO_ITEMS_KEY)){
            result.setItems(savedInstanceState.getStringArrayList(TODO_ITEMS_KEY));
        }
    }

    private TodoVM build() {
        mAdapter = new ArrayAdapter<>(mContext, android.R.layout.simple_list_item_1, items);

        mBinding.listView2.setAdapter(mAdapter);

        setRemoveTaskOnLongItemClick();

        return this;
    }

    private void setRemoveTaskOnLongItemClick() {
        mBinding.listView2.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                mAdapter.remove(items.get(position));

                mAdapter.notifyDataSetChanged();

                persistItems();

                return true;
            }
        });
    }
    //endregion
}
