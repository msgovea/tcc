package com.mgovea.urmusic;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.FloatRange;
import android.view.View;

import agency.tango.materialintroscreen.MaterialIntroActivity;
import agency.tango.materialintroscreen.SlideFragmentBuilder;
import agency.tango.materialintroscreen.animations.IViewTranslation;

public class IntroActivity extends MaterialIntroActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        enableLastSlideAlphaExitTransition(true);
        //setSkipButtonVisible();
        enableLastSlideAlphaExitTransition(true);

        getBackButtonTranslationWrapper()
                .setEnterTranslation(new IViewTranslation() {
                    @Override
                    public void translate(View view, @FloatRange(from = 0, to = 1.0) float percentage) {
                        view.setAlpha(percentage);
                    }
                });

        addSlide(new SlideFragmentBuilder()
                        .backgroundColor(R.color.primary_dark)
                        .buttonsColor(R.color.accent)
                        .image(R.drawable.logo_m)
                        .title("\n" + getString(R.string.intro_title))
                        .description("\n\n" +getString(R.string.intro_message))
                        .build());

        addSlide(new SlideFragmentBuilder()
                .backgroundColor(R.color.primary_dark)
                .buttonsColor(R.color.accent)
                .image(R.drawable.mock_png)
                .title("\n" + getString(R.string.intro_title1))
                .description("\n\n" + getString(R.string.intro_desc1))
                .build());

        addSlide(new SlideFragmentBuilder()
                .backgroundColor(R.color.primary_dark)
                .buttonsColor(R.color.accent)
                .image(R.drawable.publi_1)
                .title("\n" + getString(R.string.intro_title4))
                .description("\n\n" + getString(R.string.intro_desc4))
                .build());

        addSlide(new SlideFragmentBuilder()
                .backgroundColor(R.color.primary_dark)
                .buttonsColor(R.color.accent)
                .image(R.drawable.publi_2)
                .title("\n" + getString(R.string.intro_title5))
                .description("\n\n" + getString(R.string.intro_desc5))
                .build());


        addSlide(new SlideFragmentBuilder()
                        .backgroundColor(R.color.primary_dark)
                        .buttonsColor(R.color.accent)
                        .image(R.drawable.perfil)
                        .title("\n" + getString(R.string.intro_title3))
                        .description("\n\n" + getString(R.string.intro_message3))
                        .build());
    }


    @Override
    protected void onPause(){
        super.onPause();
        finish();
    }
}