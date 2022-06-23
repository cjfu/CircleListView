package com.example.circlelistview;

import android.view.View;

import java.util.HashMap;
import java.util.Map;

import static com.example.circlelistview.CircleListView.intervalAngel;

public class ViewHolder {
    Map<Integer, View> views = new HashMap<>();

    CircleListView circleListView;

    public ViewHolder(CircleListView circleListView) {
        this.circleListView = circleListView;
    }

    public void add(int position, View view) {
        double viewAngel = position * intervalAngel + circleListView.angel + 90;
        if (viewAngel > 270 || viewAngel < -90) {//超出显示范围的暂不添加
            return;
        }
        for (Integer integer : views.keySet()) {
            double childViewAngel = integer * intervalAngel + circleListView.angel + 90;
            if (childViewAngel > 270 || childViewAngel < -90) {
                views.put(position, views.remove(integer));
                return;
            }
        }
        views.put(position, view);
        circleListView.addView(view);
    }

    public View getViewByPosition(int position) {
        return views.get(position);
    }
}
