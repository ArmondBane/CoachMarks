package com.getkeepsafe.taptargetviewsample;

import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.Display;
import android.widget.TextView;
import android.widget.Toast;

import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetSequence;
import com.getkeepsafe.taptargetview.TapTargetView;

public class MainActivity extends AppCompatActivity {
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    toolbar.inflateMenu(R.menu.menu_main);
    toolbar.setNavigationIcon(ContextCompat.getDrawable(this, R.drawable.ic_arrow_back_white_24dp));

    // We load a drawable and create a location to show a tap target here
    // We need the display to get the width and height at this point in time
    final Display display = getWindowManager().getDefaultDisplay();
    // Load our little droid guy
    final Drawable droid = ContextCompat.getDrawable(this, R.drawable.ic_android_black_24dp);
    final Drawable arrow = ContextCompat.getDrawable(this, R.drawable.ic_vector);
    // Tell our droid buddy where we want him to appear
    final Rect droidTarget = new Rect(0, 0, droid.getIntrinsicWidth() * 2, droid.getIntrinsicHeight() * 2);
    // Using deprecated methods makes you look way cool
    droidTarget.offset(display.getWidth() / 2, display.getHeight() / 2);

    final SpannableString sassyDesc = new SpannableString("It allows you to go back, sometimes");
    sassyDesc.setSpan(new StyleSpan(Typeface.ITALIC), sassyDesc.length() - "sometimes".length(), sassyDesc.length(), 0);

    // We have a sequence of targets, so lets build it!
    final TapTargetSequence sequence = new TapTargetSequence(this)
        .targets(
            // This tap target will target the back button, we just need to pass its containing toolbar
            TapTarget.forToolbarNavigationIcon(toolbar, "This is the back button", sassyDesc, null).id(1).transparentTarget(true)
                    .targetArrowDrawable(arrow, 40),
            // Likewise, this tap target will target the search button
            TapTarget.forToolbarMenuItem(toolbar, R.id.search, "This is a search icon", "As you can see, it has gotten pretty dark around here...", null)
                .dimColor(android.R.color.black)
                .outerCircleColor(R.color.colorAccent)
                .targetCircleColor(android.R.color.black)
                .transparentTarget(true)
                .textColor(android.R.color.black)
                .targetArrowDrawable(arrow, 20)
                .id(2),
            // You can also target the overflow button in your toolbar
            TapTarget.forToolbarOverflow(toolbar, "This will show more options", "But they're not useful :(", null).id(3).transparentTarget(true)
                    .targetArrowDrawable(arrow, 20),
            // This tap target will target our droid buddy at the given target rect
            TapTarget.forBounds(droidTarget, "Oh look!", "You can point to any part of the screen. You also can't cancel this one!")
                .cancelable(false)
                .icon(droid)
                .targetArrowDrawable(arrow, 20)
                .id(4)
        )
        .listener(
          new TapTargetSequence.Listener() {
            // This listener will tell us when interesting(tm) events happen in regards
            // to the sequence
            @Override
            public void onSequenceFinish() {
              ((TextView) findViewById(R.id.educated)).setText("Congratulations! You're educated now!");
            }

            @Override
            public void onSequenceStep(TapTarget lastTarget, boolean targetClicked) {
              Log.d("TapTargetView", "Clicked on " + lastTarget.id());
            }

            @Override
            public void onSequenceCanceled(TapTarget lastTarget) {
              final AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
                  .setTitle("Uh oh")
                  .setMessage("You canceled the sequence")
                  .setPositiveButton("Oops", null).show();

              TapTargetView.showFor(dialog,
                TapTarget.forView(
                  dialog.getButton(DialogInterface.BUTTON_POSITIVE),
                  "Uh oh!", "You canceled the sequence at step " + lastTarget.id()
                )
                  .cancelable(false)
                  .targetArrowDrawable(arrow),
                  new TapTargetView.Listener() {
                    @Override
                    public void onTargetClick(TapTargetView view) {
                      super.onTargetClick(view);
                      dialog.dismiss();
                    }
                  }
              );
            }
          }
        );

    // You don't always need a sequence, and for that there's a single time tap target
    final SpannableString spannedDesc = new SpannableString("This is the sample app for TapTargetView");
    spannedDesc.setSpan(new UnderlineSpan(), spannedDesc.length() - "TapTargetView".length(), spannedDesc.length(), 0);

    TapTargetView.showFor(
      this,
      TapTarget.forView(findViewById(R.id.fab),
        "Hello, world!",
        spannedDesc,
        "Understand"
      )
        .targetArrowDrawable(arrow, 20, 0.3f)
        .cancelable(false)
        .drawShadow(true)
        .titleTextDimen(R.dimen.title_text_size)
        .transparentTarget(true)
        .customElement(
           new TapTargetView.ICustomElement() {
               @Override
               public void draw(Canvas canvas, int alpha) {
                 final Paint paint = new Paint();
                   paint.setAntiAlias(true);
                   paint.setColor(Color.parseColor("#456FCC"));
                   paint.setStrokeWidth(5);
                   paint.setStyle(Paint.Style.FILL);
                   paint.setAlpha(alpha);

                final Rect rect = new Rect(0,20,60,60);
                canvas.drawRect(rect, paint);
               }
               @NonNull
               @Override
               public Rect getClickBounds(int startX, int startY) {
                   return new Rect(startX, startY, startX + 60, startY + 60);
               }
               @NonNull
               @Override
               public Rect getDrawBounds(int startX, int startY) {
                   return new Rect(startX, startY, startX + 60, startY + 60);
               }
           }
        )
        .targetRadius(25),
        new TapTargetView.Listener() {
            @Override
            public void onButtonClick(TapTargetView view) {
              super.onButtonClick(view);
              sequence.start();
            }
            @Override
            public void onTargetClick(TapTargetView view) { }
            @Override
            public void onCustomElementClick(TapTargetView view) {
                Toast.makeText(view.getContext(), "custom Click!", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onOuterCircleClick(TapTargetView view) {
              super.onOuterCircleClick(view);
              Toast.makeText(view.getContext(), "You clicked the outer circle!", Toast.LENGTH_SHORT).show();
            }
        }
    );

  }
}
