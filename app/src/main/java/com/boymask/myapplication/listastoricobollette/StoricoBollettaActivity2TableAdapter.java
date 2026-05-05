package com.boymask.myapplication.listastoricobollette;


import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.boymask.myapplication.DBHandler;
import com.boymask.myapplication.DettaglioStoricoBollettaActivity;
import com.boymask.myapplication.R;
import com.boymask.myapplication.database.Bolletta;

import java.io.OutputStream;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class StoricoBollettaActivity2TableAdapter extends RecyclerView.Adapter<StoricoBollettaActivity2TableAdapter.ViewHolder> {


    private List<StoricoBollettaActivity2RowModel> lista;
    private Context context;

    private int selectedPosition = -1;

    public StoricoBollettaActivity2TableAdapter(Context context, List<StoricoBollettaActivity2RowModel> lista) {
        this.context = context;
        this.lista = lista;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtTitolo;
        RadioButton radioButton;
        ImageView btnMenu;

        public ViewHolder(View itemView) {
            super(itemView);
            txtTitolo = itemView.findViewById(R.id.txtTitolo);
          //  radioButton = itemView.findViewById(R.id.radioButton);
            btnMenu = itemView.findViewById(R.id.btnMenu);
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        StoricoBollettaActivity2RowModel item = lista.get(position);

        holder.txtTitolo.setText(toDateString(item.getTitolo()));

        boolean isSelected = position == selectedPosition;

//        holder.radioButton.setOnCheckedChangeListener(null);
//        holder.radioButton.setChecked(isSelected);

        holder.itemView.setBackgroundColor(
                isSelected ? Color.LTGRAY : Color.TRANSPARENT
        );

        // selezione
        holder.itemView.setOnClickListener(v -> selectItem(position));
  //      holder.radioButton.setOnClickListener(v -> selectItem(position));

        // 🔥 MENU OPZIONI
        holder.btnMenu.setOnClickListener(v -> {
            PopupMenu popup = new PopupMenu(context, holder.btnMenu);
            popup.inflate(R.menu.menu_item);

            popup.setOnMenuItemClickListener(menuItem -> {

                if (menuItem.getItemId() == R.id.action_view) {
                //    Toast.makeText(context, "Modifica: " + item.getTitolo(), Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(context, DettaglioStoricoBollettaActivity.class);
                    intent.putExtra("id", item.getId()); // assicurati che esista getId()
                    context.startActivity(intent);

                    return true;
                }

              /*  if (menuItem.getItemId() == R.id.action_delete) {
                    lista.remove(position);
                    notifyItemRemoved(position);
                    return true;
                }*/
                if (menuItem.getItemId() == R.id.action_delete) {

                    new AlertDialog.Builder(context)
                            .setTitle("Conferma eliminazione")
                            .setMessage("Vuoi eliminare \"" + toDateString(item.getTitolo()) + "\"?")
                            .setPositiveButton("Elimina", (dialog, which) -> {

                                lista.remove(position);
                                notifyItemRemoved(position);
                                notifyItemRangeChanged(position, lista.size());

                                Toast.makeText(context, "Elemento eliminato", Toast.LENGTH_SHORT).show();
                            })
                            .setNegativeButton("Annulla", (dialog, which) -> {
                                dialog.dismiss();
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();

                    return true;
                }

                if (menuItem.getItemId() == R.id.action_scarica) {
                    new Thread(() -> {
                    Bolletta b = DBHandler.getStoricoBollettaById(item.getId());
                    ContentValues values = new ContentValues();
                    values.put(MediaStore.Downloads.DISPLAY_NAME, "Bolletta "+toDateString(item.getTitolo()));
                    values.put(MediaStore.Downloads.MIME_TYPE, "text/plain");
                    values.put(MediaStore.Downloads.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS);

                    Uri uri = context.getContentResolver().insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, values);

                    try {
                        OutputStream os = context.getContentResolver().openOutputStream(uri);
                        os.write(b.analisi.getBytes());
                        os.close();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }   }).start();
                    return true;
                }

                return false;
            });

            popup.show();
        });
    }
    private String toDateString(String  stimestamp) {
        long timestamp = Long.parseLong(stimestamp);

        LocalDateTime dateTime = Instant.ofEpochMilli(timestamp)
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        return dateTime.format(formatter);
    }
    private void selectItem(int position) {
        int previous = selectedPosition;
        selectedPosition = position;

        notifyItemChanged(previous);
        notifyItemChanged(selectedPosition);
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.storico_bollette2item_row, parent, false);
        return new ViewHolder(view);
    }

    public StoricoBollettaActivity2RowModel getSelectedItem() {
        if (selectedPosition >= 0 && selectedPosition < lista.size()) {
            return lista.get(selectedPosition);
        }
        return null;
    }
}