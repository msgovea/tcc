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
                        .title("Organize your time with us")
                        .description("Would you try?")
                        .build(),
                new MessageButtonBehaviour(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showMessage("We provide solutions to make you love your work");
                    }
                }, "Work with love"));

        addSlide(new SlideFragmentBuilder()
                .backgroundColor(R.color.primary_light)
                .buttonsColor(R.color.accent)
                .title("Want more?")
                .description("Go on")
                .build());

        addSlide(new SlideFragmentBuilder()
                        .backgroundColor(R.color.primary_dark)
                        .buttonsColor(R.color.accent)
                        .possiblePermissions(new String[]{android.Manifest.permission.USE_FINGERPRINT})
                        .neededPermissions(new String[]{android.Manifest.permission.USE_FINGERPRINT})
                        .image(R.drawable.img_equipment)
                        .title("We provide best tools")
                        .description("ever")
                        .build(),
                new MessageButtonBehaviour(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showMessage("Try us!");
                    }
                }, "Tools"));

        addSlide(new SlideFragmentBuilder()
                .backgroundColor(R.color.primary_text)
                .buttonsColor(R.color.accent)
                .title("That's it")
                .description("Would you join us?")
                .build());
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