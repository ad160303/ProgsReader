package com.hw.progsreader;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.hw.progsreader.objs.Option;
import com.hw.progsreader.utils.FileArrayAdapter;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Administrator on 2016/7/11.
 */
public class FileChooser extends ListActivity {
    private File currentDir;
    private File fileSelected;
    private FileFilter fileFilter;
    private FileArrayAdapter adapter;
    private ArrayList<String> extensions;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        if (extras != null){
            if (extras.getStringArrayList("fileFilterExtension") != null){
                extensions = extras.getStringArrayList("fileFilterExtension");
                fileFilter = new FileFilter() {
                    @Override
                    public boolean accept(File pathname) {
                        return ((pathname.isDirectory()) || (pathname.getName().contains(".")?extensions.contains(pathname.getName().substring(pathname.getName().lastIndexOf("."))):false));
                    }
                };
            }
        }
        currentDir = new File("/sdcard/");
        fill(currentDir);
    }
    private void fill(File f){
        File[] dirs = null;
        if(fileFilter != null){
            dirs = f.listFiles(fileFilter);
        } else{
            dirs = f.listFiles();
        }
        this.setTitle("Current dir : " + f.getName());
        List<Option> dir = new ArrayList<Option>();
        List<Option> fls = new ArrayList<Option>();
        try{
            for(File ff:dirs){
                if (ff.isDirectory() && !ff.isHidden()){
                    dir.add(new Option(ff.getName(), "Folder", ff.getAbsolutePath(), true, false));
                }else{
                    if (!ff.isHidden()){
                        fls.add(new Option(ff.getName(), "File Size : " + ff.length(), ff.getAbsolutePath(), false, false));
                    }
                }
            }
        }catch (Exception ex){}

        Collections.sort(dir);
        Collections.sort(fls);
        dir.addAll(fls);
        if (!f.getName().equalsIgnoreCase("sdcard")){
            if (f.getParentFile() != null){
                dir.add(0, new Option("..", "Parent Directory", f.getParent(), false, true));
            }
        }
        adapter = new FileArrayAdapter(FileChooser.this, R.layout.file_view, dir);
        this.setListAdapter(adapter);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event){
        if (keyCode == KeyEvent.KEYCODE_BACK){
            if ((!currentDir.getName().equals("sdcard")) && (currentDir.getParentFile() != null)){
                currentDir = currentDir.getParentFile();
                fill(currentDir);
            } else{
                finish();
            }
            return false;
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onListItemClick(ListView l, View v,int position, long id){
        super.onListItemClick(l, v, position, id);
        Option o = adapter.getItem(position);
        if (o.isFolder() || o.isPatent()){
            currentDir = new File(o.getPath());
            fill(currentDir);
        } else{
            fileSelected = new File(o.getPath());
            Intent intent = new Intent(FileChooser.this, ReaderActivity.class);
            intent.putExtra("file_selected", fileSelected.getAbsolutePath());
            startActivity(intent);
        }
    }
}
