package com.f1mert.flowlayout.simpleFlowLayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import com.f1mert.flowlayout.R;
import com.f1mert.flowlayout.simpleFlowLayout.bean.MarginModel;
import com.f1mert.flowlayout.simpleFlowLayout.bean.TagModel;

/**
 * 多选流式布局
 * * @author  f1mert
 */
public class CheckFlowLayout extends ViewGroup {
    /**
     * item项行高
     */
    private float itemHeight;
    /**
     * item项横向间隔
     */
    private float itemSpace;
    /**
     * 列间隔
     */
    private float dividerHeight;

    public CheckFlowLayout(Context context) {
        super(context);
    }

    public CheckFlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.flowlayout);
        itemHeight = typedArray.getDimension(R.styleable.flowlayout_itemHeight,0);
        itemSpace = typedArray.getDimension(R.styleable.flowlayout_itemSpace,0);
        dividerHeight = typedArray.getDimension(R.styleable.flowlayout_dividerHeight,0);
        typedArray.recycle();
    }

    public float getItemHeight() {
        return itemHeight;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);
        //总宽度
        int width = 0;
        //总高度
        int height = 0;
        //item 所处行数
        int lineIndex = 0;
        //行宽
        int lineWidth = 0;

        int marginLeft = 0;
        int marginTop = 0;
        int childCount = getChildCount();
        if(childCount > 0){
            for(int i = 0;i < childCount;i++){

                View child = getChildAt(i);
                measureChild(child,widthMeasureSpec,heightMeasureSpec);
                int childWidth = child.getMeasuredWidth();

                if(childWidth + itemSpace + lineWidth > sizeWidth - getPaddingLeft() - getPaddingRight()){
                    lineIndex++;
                    marginLeft = 0;
                    lineWidth = childWidth;
                    marginTop += (itemHeight + dividerHeight);
                }else{
                    lineWidth += childWidth + itemSpace*(i>0?1:0);
                    marginLeft = lineWidth - childWidth;
                }

                int left = marginLeft + getPaddingLeft();
                int right = marginLeft + childWidth+getPaddingLeft();
                int top = marginTop + getPaddingTop();
                int bottom = (int) (marginTop + itemHeight+ getPaddingTop());

                TagModel tagModel;
                Object object = child.getTag();
                MarginModel marginModel = new MarginModel(left,top,right,bottom);

                if(object instanceof TagModel)
                    tagModel = (TagModel) object;
                else
                    tagModel = new TagModel();
                tagModel.setMarginModel(marginModel);
                child.setTag(tagModel);
            }
            height = (int) (itemHeight*(lineIndex+1) + dividerHeight*lineIndex + getPaddingTop() + getPaddingBottom());
        }
        setMeasuredDimension(modeWidth == MeasureSpec.EXACTLY?sizeWidth:width,modeHeight == MeasureSpec.EXACTLY?sizeHeight:height);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        for(int i = 0;i<getChildCount();i++){
            View view = getChildAt(i);
            Object object = view.getTag();
            if(object instanceof TagModel){
                TagModel tagModel = (TagModel) object;
                MarginModel marginModel = tagModel.getMarginModel();
                if(marginModel != null)view.layout(marginModel.getLeft(),marginModel.getTop(),marginModel.getRight(),marginModel.getBottom());
            }
        }
    }
}
