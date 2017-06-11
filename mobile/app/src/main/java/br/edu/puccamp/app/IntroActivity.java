package br.edu.puccamp.app;

import android.os.Bundle;
import android.support.annotation.FloatRange;
import android.view.View;

import agency.tango.materialintroscreen.MaterialIntroActivity;
import agency.tango.materialintroscreen.MessageButtonBehaviour;
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
                        .image(R.drawable.img_office)
                        .title(getString(R.string.intro_title))
                        .description(getString(R.string.intro_message))
                        .build());
//                new MessageButtonBehaviour(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        showMessage("We provide solutions to make you love your work");
//                    }
//                }, getString(R.string.intro_button)));

        addSlide(new SlideFragmentBuilder()
                .backgroundColor(R.color.primary_light)
                .buttonsColor(R.color.accent)
                .title(getString(R.string.intro_title2))
                .description(getString(R.string.intro_message2))
                .build());

        addSlide(new SlideFragmentBuilder()
                        .backgroundColor(R.color.primary_dark)
                        .buttonsColor(R.color.accent)
                        //.possiblePermissions(new String[]{android.Manifest.permission.USE_FINGERPRINT})
                        //.neededPermissions(new String[]{android.Manifest.permission.USE_FINGERPRINT})
                        .image(R.drawable.img_equipment)
                        .title(getString(R.string.intro_title3))
                        .description(getString(R.string.intro_message3))
                        .build());
//                new MessageButtonBehaviour(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        showMessage("Try us!");
//                    }
//                }, getString(R.string.intro_button2)));

//        addSlide(new SlideFragmentBuilder()
//                .backgroundColor(R.color.primary_text)
//                .buttonsColor(R.color.accent)
//                .title(getString(R.string.intro_title4))
//                .description(getString(R.string.intro_message4))
//                .build());
    }

//    @Override
//    public void onFinish() {
//        startActivity(new Intent(this, MainActivity.class));
//        Toast.makeText(this, "Try this library in your project! :)", Toast.LENGTH_SHORT).show();
//    }

    @Override
    protected void onPause(){
        super.onPause();
        finish();
    }
}