package com.gcssloop.scalegesturedemo;

import android.animation.TypeEvaluator;

public class MyEvauator implements TypeEvaluator<Integer> {
    @Override
    public Integer evaluate(float v, Integer integer, Integer t1) {
        return (int)(integer+(t1-integer)*v);
    }
}
