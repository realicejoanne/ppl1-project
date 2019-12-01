package com.anjass.raihan.monica20.Fragment;

import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;

import com.anjass.raihan.monica20.Adapter.File_Adapter;
import com.anjass.raihan.monica20.Class.File_Class;
import com.anjass.raihan.monica20.R;

import java.util.ArrayList;

public class FilesFragment extends Fragment {

    File_Adapter filesAdapter;
    ArrayList<File_Class> files;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_files, container, false);

        files = new ArrayList<>();
        files.add(new File_Class("Dokumentasi",R.drawable.icon_folder));
        files.add(new File_Class("Surat-surat",R.drawable.icon_folder));
        files.add(new File_Class("Design",R.drawable.icon_folder));
        files.add(new File_Class("Logo HD.png",R.drawable.icon_file));
        files.add(new File_Class("Logo HD.jpg",R.drawable.icon_file));
        files.add(new File_Class("Add new folder...",R.drawable.icon_folder_plus));
        files.add(new File_Class("Add new file...",R.drawable.icon_file_plus));

        GridView gridView = (GridView)view.findViewById(R.id.grid_files);
        filesAdapter = new File_Adapter(getActivity(), files);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                File_Class fileItemAdapter = files.get(position);
                //Toast.makeText(FileCloud.this, "Posisi: "+position, Toast.LENGTH_SHORT).show();
                if (position == (files.size()-2)) {
                    files.add((files.size()-2),new File_Class("New folder " +(files.size()-6), R.drawable.icon_folder));
                }
                if (position == (files.size()-1)){
                    files.add((files.size()-2),new File_Class("New file" +(files.size()-6), R.drawable.icon_file));
                }
                filesAdapter.notifyDataSetChanged();
            }
        });
        gridView.setAdapter(filesAdapter);

        return view;
    }
}
