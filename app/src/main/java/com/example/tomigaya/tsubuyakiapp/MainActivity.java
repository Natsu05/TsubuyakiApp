package com.example.tomigaya.tsubuyakiapp;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements
        View.OnClickListener, AdapterView.OnItemLongClickListener{

    //つぶやき表示用リストビュー
    private ListView tsubuyakiLV;

    //つぶやき入力欄
    private EditText commentEtx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //レイアウトより、つぶやく情報を取得
        Button commitbtn = (Button)findViewById(R.id.main_commit_btn);
        commitbtn.setOnClickListener(this);

        //レイアウトより、リストビューの情報を取得
        tsubuyakiLV = (ListView)findViewById(R.id.main_tsubuyaki_lv);
        tsubuyakiLV.setOnItemLongClickListener(this);

        //リストビューの内容を更新する
        updateListView();

    }

    @Override
    public void onClick(View v) {
        //レイアウトより、入力欄の情報を取得
        commentEtx =(EditText)findViewById(R.id.main_comment_etx);

        Tsubuyaki tsubuyaki = new Tsubuyaki();
        tsubuyaki.id = 1;
        tsubuyaki.comment = commentEtx.getText().toString();
        tsubuyaki.save();//テーブルに保存

        updateListView();
        commentEtx.setText("");//入力欄を空にする
    }
    //リストビューの内容を更新する
    private  void updateListView(){

        //テーブルから全てのデータを取得
        List<Tsubuyaki> list = Tsubuyaki.listAll(Tsubuyaki.class);
        //昇順(ASC):小さい順
        //降順(DESC):大きい順
        list = Tsubuyaki.listAll(Tsubuyaki.class,"ID DESC");


        //リストビューにデータをセット
        //Adapter:特定のデータをひとまとめにしてビューに渡す時に利用する
        AdapterListTsubuyaki adapter =
                new AdapterListTsubuyaki(this,R.layout.list_tsubuyaki,list);
        tsubuyakiLV.setAdapter(adapter);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

        ListView list = (ListView) adapterView;

        final Tsubuyaki selectedItem = (Tsubuyaki) list.getItemAtPosition(i);

        new AlertDialog.Builder(this)
                .setTitle("削除")
                .setMessage("削除してもよろしいですか？")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Tsubuyaki tsubuyaki = Tsubuyaki.findById(Tsubuyaki.class, selectedItem.getId());
                        tsubuyaki.delete();

                        updateListView();
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();

        return false;
    }
    }
