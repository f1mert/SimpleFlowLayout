package com.f1mert.flowlayout;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.f1mert.flowlayout.simpleFlowLayout.bean.BrandModel;
import com.f1mert.flowlayout.simpleFlowLayout.CheckFlowLayout;
import com.f1mert.flowlayout.simpleFlowLayout.RadioFlowLayout;
import com.f1mert.flowlayout.simpleFlowLayout.bean.TagModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private CheckFlowLayout checkFlowLayout;
    private RadioFlowLayout radioFlowLayout;
    List<BrandModel> brandList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        checkFlowLayout = this.findViewById(R.id.checkFlowLayout);
        radioFlowLayout = this.findViewById(R.id.radioFlowLayout);
        for(int i = 0;i<brandList.size();i++){
            checkFlowLayout.addView(createChildView(brandList.get(i),R.layout.tag_checkbox));
        }

        for(int i = 0;i<brandList.size();i++){
            radioFlowLayout.addView(createChildView(brandList.get(i),R.layout.tag_radiobutton));
        }
    }

    private <T extends TextView> View createChildView(BrandModel brandModel,int layoutId) {
        LayoutInflater inflater = LayoutInflater.from(this);
        T view = (T) inflater.inflate(layoutId, null);
        RadioGroup.LayoutParams lp = new RadioGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.gravity = Gravity.CENTER;
        lp.height = (int) radioFlowLayout.getItemHeight();
        view.setLayoutParams(lp);
        //添加view的时候，将品牌信息 setTag()
        TagModel<BrandModel> tagModel = new TagModel<BrandModel>();
        tagModel.setT(brandModel);
        view.setTag(tagModel);
        view.setText(brandModel.getName());
        return view;
    }

    private void initData(){
        String[] brands = new String[]{"捷豹","施华洛世奇","雷朋","Emporio Armani","海伦凯勒",
                                        "精工","HORIEN海俪恩","CHARMANT","COACH蔻驰","李维斯","新百伦"
        };
//        String[] brands = new String[]{"捷豹","施华洛世奇","Emporio Armani","雷朋","海伦凯勒",
//                                        "精工","HORIEN海俪恩","CHARMANT","COACH蔻驰","李维斯","新百伦"
//                                        ,"POLICE","Sting","FURLA","皇家马德里","GUESS","老佛爷","TOM FORD"
//        };

        for(int i=0;i<brands.length;i++){
            BrandModel brandModel = new BrandModel();
            brandModel.setId(i);brandModel.setName(brands[i]);
            brandList.add(brandModel);
        }
    }
}
