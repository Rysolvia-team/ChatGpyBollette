package com.boymask.myapplication;

import android.os.Bundle;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.boymask.myapplication.database.Bolletta;
import com.boymask.myapplication.listastoricobollette.StoricoBollettaActivity2RowModel;
import com.boymask.myapplication.listastoricobollette.StoricoBollettaActivity2TableAdapter;

import java.util.ArrayList;
import java.util.List;

    public class StoricoBollettaActivity2 extends AppCompatActivity {

        private RecyclerView recyclerView;
        private StoricoBollettaActivity2TableAdapter adapter;
        private List<StoricoBollettaActivity2RowModel> lista;

        private ActionMode actionMode;

        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_storico_bolletta2);

            recyclerView = findViewById(R.id.recyclerView);

            Button esci = findViewById(R.id.esci);
            esci.setOnClickListener(v -> {

                StoricoBollettaActivity2.this.finish();

            });
            // dati esempio
            lista = new ArrayList<>();

            buildListaRighe();

        }
        private void buildListaRighe() {
            new Thread(() -> {
                List<Bolletta> bollette = DBHandler.getStoricoBollette();

                List<StoricoBollettaActivity2RowModel> rows = buildRows(bollette);
                runOnUiThread(() -> {
                    adapter = new StoricoBollettaActivity2TableAdapter(this, rows );

                    StoricoBollettaActivity2RowModel selected = adapter.getSelectedItem();
                    if (selected != null) {
                        Toast.makeText(this,
                                "Selezionato: " + selected.getTitolo(),
                                Toast.LENGTH_SHORT).show();
                    }

                    recyclerView.setLayoutManager(new LinearLayoutManager(this));
                    recyclerView.setAdapter(adapter);});
            }).start();
        }
        private List<StoricoBollettaActivity2RowModel> buildRows(List<Bolletta> bollette) {
            List<StoricoBollettaActivity2RowModel> rows = new ArrayList<>();
            for (Bolletta b : bollette) {
                rows.add(new StoricoBollettaActivity2RowModel(b));
            }
            return rows;
        }
        // CALLBACK TOOLBAR CONTESTUALE
        private final ActionMode.Callback actionModeCallback = new ActionMode.Callback() {

            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                MenuInflater inflater = mode.getMenuInflater();
                inflater.inflate(R.menu.context_menu, menu);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {

                if (item.getItemId() == R.id.action_delete) {
             //       adapter.deleteSelected();
                    mode.finish();
                    return true;
                }

                if (item.getItemId() == R.id.action_select_all) {
               //     adapter.selectAll();
                    return true;
                }

                return false;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {
         //       adapter.clearSelection();
                actionMode = null;
            }
        };
    }
