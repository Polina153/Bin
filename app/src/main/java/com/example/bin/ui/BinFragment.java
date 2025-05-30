package com.example.bin.ui;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bin.R;
import com.example.bin.data.SavedItem;
import com.example.bin.databinding.FragmentBinBinding;
import com.example.bin.ui.state.BinScreenState;
import com.example.bin.ui.state.Error;
import com.example.bin.ui.state.Loading;
import com.example.bin.ui.state.SuccessfulUpdate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class BinFragment extends Fragment {

    private FragmentBinBinding binding;
    private BinViewModel viewModel;
    private BinAdapter recyclerViewAdapter;
    private final BinAdapter.OnListChangedListener listener = new BinAdapter.OnListChangedListener() {
        /*
        Отдельной кнопки Save нет, поэтому приходится сохранять список товаров при любом изменении
        в корзине (onAddItem, onRemoveItem, onDismissItem)
        */
        @Override
        public void onAddItem(SavedItem item) {
            viewModel.addProductToBin(item);
        }

        @Override
        public void onRemoveItem(SavedItem item) {
            viewModel.removeItemFromBin(item);
        }

        @Override
        public void onDismissItem(SavedItem item) {
            viewModel.dismissItemFromBin(item);
        }
    };

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentBinBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = binding.recyclerView;
        recyclerViewAdapter = new BinAdapter(new ArrayList<>(), listener);
        recyclerView.setAdapter(recyclerViewAdapter);
        implementSwipeGesture(recyclerView);

        viewModel = new ViewModelProvider(this, ViewModelProvider.Factory.from(BinViewModel.initializer)).get(BinViewModel.class);
        viewModel.getData().observe(getViewLifecycleOwner(), this::renderState);
        viewModel.getProductList(readJsonFromAssets());

        //FIXME Добавляем новый продукт в список
        //В дизайне не заложена данная функциональность, поэтому добавляем продукт в корзину
        //через кнопку Продолжить
        binding.proceedButton.setOnClickListener(v -> viewModel.addItemToBin());
    }

    private void implementSwipeGesture(RecyclerView recyclerView) {
        new ItemTouchHelper(new ItemTouchHelperCallback(recyclerViewAdapter))
                .attachToRecyclerView(recyclerView);
    }

    private void renderState(BinScreenState appState) {
        if (appState instanceof SuccessfulUpdate) {
            onNewData((SuccessfulUpdate) appState);
        } else if (appState instanceof com.example.bin.ui.state.Error) {
            Throwable error = ((Error) appState).getError();
            Toast.makeText(requireContext(), error.getMessage() + error, Toast.LENGTH_SHORT).show();
        } else if (appState instanceof Loading) {
            //Обработка состояния загрузки
            Log.d("TAG", "Loading");
        } else {
            //Обработка состояния покоя
            Log.d("TAG", "Idle");
        }
    }

    private void onNewData(SuccessfulUpdate update) {
        //Обновляем UI на основе нового состояния
        SavedData savedData = update.getBinSavedData();
        List<SavedItem> savedItems = update.getBinSavedData().getSavedItems();

        binding.headerTotalCount.setText(getString(R.string.header_total_count, Integer.toString(savedData.getItemsCount()))+ " " + getCorrectEnding(savedData.getItemsCount()));
        binding.headerTotalSum.setText(getString(R.string.header_total_price, Integer.toString(savedData.getTotalPrice())));

        binding.headerSpinnerTotalCount.setText(getString(R.string.spinner_total_count, Integer.toString(savedData.getItemsCount())));
        binding.footerSum.setText(getString(R.string.footer_total_price, Integer.toString(savedData.getTotalPrice())));

        if (savedItems.isEmpty()) {
            binding.footerOldSum.setVisibility(View.GONE);
        } else {
            binding.footerOldSum.setVisibility(View.VISIBLE);
            binding.footerOldSum.setText(getString(R.string.footer_total_price_old, Integer.toString(savedData.getTotalPriceOld())));
        }

        recyclerViewAdapter.dataSetChange(savedItems);
    }

    private String getCorrectEnding(int number) {
        if (number % 10 == 1 && number % 100 != 11) {
            return "товар";
        } else if (number % 10 >= 2 && number % 10 <= 4 && (number % 100 < 10 || number % 100 >= 20)) {
            return "товара";
        } else {
            return "товаров";
        }
    }

    /*
   FIXME получать данные из ViewModel
   Лучше получать данные во ViewModel, но для этого там нужен Context.
   Контекст можно получить как
   BinViewModel(applicationContext: Application) : AndroidViewModel(applicationContext),
   но использование Контекста без крайней необходимости во ViewModel - это плохая практика.
   Так как данные все равно являются заглушкой, то будем просто передавать сырой Json во ViewModel
   там уже обрабатывать
   */
    private String readJsonFromAssets() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(requireContext().getAssets().open("tovary_i_kategorii.json")));
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line);
            }
            return content.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
