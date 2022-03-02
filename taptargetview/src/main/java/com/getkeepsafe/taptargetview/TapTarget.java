/**
 * Copyright 2016 Keepsafe Software, Inc.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.getkeepsafe.taptargetview;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.DimenRes;
import androidx.annotation.IdRes;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.appcompat.widget.Toolbar;
import android.view.View;

/**
 * Describes the properties and options for a {@link TapTargetView}.
 * <p>
 * Each tap target describes a target via a pair of bounds and icon. The bounds dictate the
 * location and touch area of the target, where the icon is what will be drawn within the center of
 * the bounds.
 * <p>
 * This class can be extended to support various target types.
 *
 * @see ViewTapTarget ViewTapTarget for targeting standard Android views
 */
public class TapTarget {
  final CharSequence title;
  @Nullable
  final CharSequence description;

  @Nullable
  final CharSequence buttonText;

  float outerCircleAlpha = 0.96f;
  int targetRadius = 44;

  Rect bounds;

  Drawable icon;

  Drawable targetArrowDrawable;
  Integer targetArrowPadding;
  Float targetArrowBias;

  Typeface titleTypeface;
  Typeface descriptionTypeface;
  Typeface buttonTextTypeface;

  @ColorRes
  private int outerCircleColorRes = -1;
  @ColorRes
  private int targetCircleColorRes = -1;
  @ColorRes
  private int dimColorRes = -1;
  @ColorRes
  private int titleTextColorRes = -1;
  @ColorRes
  private int descriptionTextColorRes = -1;
  @ColorRes
  private int buttonTextColorRes = -1;
  @ColorRes
  private int buttonColorRes = -1;

  private Integer outerCircleColor = null;
  private Integer targetCircleColor = null;
  private Integer dimColor = null;
  private Integer titleTextColor = null;
  private Integer descriptionTextColor = null;
  private Integer buttonTextColor = null;
  private Integer buttonColor = null;

  @DimenRes
  private int titleTextDimen = -1;
  @DimenRes
  private int descriptionTextDimen = -1;
  @DimenRes
  private int buttonTextDimen = -1;
  @DimenRes
  private int buttonCornersRadiusDimen = -1;
  @DimenRes
  private int buttonVerticalPaddingDimen = -1;
  @DimenRes
  private int buttonHorizontalPaddingDimen = -1;

  private int titleTextSize = 20;
  private int descriptionTextSize = 18;
  private int buttonTextSize = 16;
  private int buttonCornersRadius = 16;
  private int buttonVerticalPadding = 6;
  private int buttonHorizontalPadding = 16;
  int id = -1;

  boolean drawShadow = false;
  boolean cancelable = true;
  boolean tintTarget = true;
  boolean transparentTarget = false;
  float descriptionTextAlpha = 0.54f;

  @Nullable
  TapTargetView.ICustomElement customElement;

  public TapTarget customElement(
    TapTargetView.ICustomElement customElement
  ) {
    this.customElement = customElement;
    return this;
  }

  /**
   * Return a tap target for the overflow button from the given toolbar
   * <p>
   * <b>Note:</b> This is currently experimental, use at your own risk
   */
  public static TapTarget forToolbarOverflow(Toolbar toolbar, CharSequence title) {
    return forToolbarOverflow(toolbar, title, null, null);
  }

  /** Return a tap target for the overflow button from the given toolbar
   * <p>
   * <b>Note:</b> This is currently experimental, use at your own risk
   */
  public static TapTarget forToolbarOverflow(Toolbar toolbar, CharSequence title,
                                                    @Nullable CharSequence description, @Nullable CharSequence buttonText) {
    return new ToolbarTapTarget(toolbar, false, title, description, buttonText);
  }

  /** Return a tap target for the overflow button from the given toolbar
   * <p>
   * <b>Note:</b> This is currently experimental, use at your own risk
   */
  public static TapTarget forToolbarOverflow(android.widget.Toolbar toolbar, CharSequence title) {
    return forToolbarOverflow(toolbar, title, null, null);
  }

  /** Return a tap target for the overflow button from the given toolbar
   * <p>
   * <b>Note:</b> This is currently experimental, use at your own risk
   */
  public static TapTarget forToolbarOverflow(android.widget.Toolbar toolbar, CharSequence title,
                                                    @Nullable CharSequence description, @Nullable CharSequence buttonText) {
    return new ToolbarTapTarget(toolbar, false, title, description, buttonText);
  }

  /** Return a tap target for the navigation button (back, up, etc) from the given toolbar **/
  public static TapTarget forToolbarNavigationIcon(Toolbar toolbar, CharSequence title) {
    return forToolbarNavigationIcon(toolbar, title, null, null);
  }

  /** Return a tap target for the navigation button (back, up, etc) from the given toolbar **/
  public static TapTarget forToolbarNavigationIcon(Toolbar toolbar, CharSequence title,
                                                          @Nullable CharSequence description, @Nullable CharSequence buttonText) {
    return new ToolbarTapTarget(toolbar, true, title, description, buttonText);
  }

  /** Return a tap target for the navigation button (back, up, etc) from the given toolbar **/
  public static TapTarget forToolbarNavigationIcon(android.widget.Toolbar toolbar, CharSequence title) {
    return forToolbarNavigationIcon(toolbar, title, null, null);
  }

  /** Return a tap target for the navigation button (back, up, etc) from the given toolbar **/
  public static TapTarget forToolbarNavigationIcon(android.widget.Toolbar toolbar, CharSequence title,
                                                   @Nullable CharSequence description, @Nullable CharSequence buttonText) {
    return new ToolbarTapTarget(toolbar, true, title, description, buttonText);
  }

  /** Return a tap target for the menu item from the given toolbar **/
  public static TapTarget forToolbarMenuItem(Toolbar toolbar, @IdRes int menuItemId,
                                             CharSequence title) {
    return forToolbarMenuItem(toolbar, menuItemId, title, null, null);
  }

  /** Return a tap target for the menu item from the given toolbar **/
  public static TapTarget forToolbarMenuItem(Toolbar toolbar, @IdRes int menuItemId,
                                             CharSequence title, @Nullable CharSequence description, @Nullable CharSequence buttonText) {
    return new ToolbarTapTarget(toolbar, menuItemId, title, description, buttonText);
  }

  /** Return a tap target for the menu item from the given toolbar **/
  public static TapTarget forToolbarMenuItem(android.widget.Toolbar toolbar, @IdRes int menuItemId,
                                             CharSequence title) {
    return forToolbarMenuItem(toolbar, menuItemId, title, null, null);
  }

  /** Return a tap target for the menu item from the given toolbar **/
  public static TapTarget forToolbarMenuItem(android.widget.Toolbar toolbar, @IdRes int menuItemId,
                                                    CharSequence title, @Nullable CharSequence description, @Nullable CharSequence buttonView) {
    return new ToolbarTapTarget(toolbar, menuItemId, title, description, buttonView);
  }

  /** Return a tap target for the specified view **/
  public static TapTarget forView(View view, CharSequence title) {
    return forView(view, title, null);
  }

  /** Return a tap target for the specified view **/
  public static TapTarget forView(View view, CharSequence title, @Nullable CharSequence description) {
    return new ViewTapTarget(view, title, description, null);
  }

  /** Return a tap target for the specified view **/
  public static TapTarget forView(View view, CharSequence title, @Nullable CharSequence description, @Nullable CharSequence buttonText) {
    return new ViewTapTarget(view, title, description, buttonText);
  }

  /** Return a tap target for the specified bounds **/
  public static TapTarget forBounds(Rect bounds, CharSequence title) {
    return forBounds(bounds, title, null);
  }

  /** Return a tap target for the specified bounds **/
  public static TapTarget forBounds(Rect bounds, CharSequence title, @Nullable CharSequence description) {
    return new TapTarget(bounds, title, description, null);
  }

  protected TapTarget(Rect bounds, CharSequence title, @Nullable CharSequence description, @Nullable CharSequence buttonText) {
    this(title, description, buttonText);
    if (bounds == null) {
      throw new IllegalArgumentException("Cannot pass null bounds or title");
    }

    this.bounds = bounds;
  }

  protected TapTarget(CharSequence title, @Nullable CharSequence description, @Nullable CharSequence buttonText) {
    if (title == null) {
      throw new IllegalArgumentException("Cannot pass null title");
    }

    this.title = title;
    this.description = description;
    this.buttonText = buttonText;
  }

  /** Specify whether the target should be transparent **/
  public TapTarget transparentTarget(boolean transparent) {
    this.transparentTarget = transparent;
    return this;
  }

  /** Specify the color resource for the outer circle **/
  public TapTarget outerCircleColor(@ColorRes int color) {
    this.outerCircleColorRes = color;
    return this;
  }

  /** Specify the color value for the outer circle **/
  // TODO(Hilal): In v2, this API should be cleaned up / torched
  public TapTarget outerCircleColorInt(@ColorInt int color) {
    this.outerCircleColor = color;
    return this;
  }

  /** Specify the alpha value [0.0, 1.0] of the outer circle **/
  public TapTarget outerCircleAlpha(float alpha) {
    if (alpha < 0.0f || alpha > 1.0f) {
      throw new IllegalArgumentException("Given an invalid alpha value: " + alpha);
    }
    this.outerCircleAlpha = alpha;
    return this;
  }

  /** Specify the color resource for the target circle **/
  public TapTarget targetCircleColor(@ColorRes int color) {
    this.targetCircleColorRes = color;
    return this;
  }

  /** Specify the color value for the target circle **/
  // TODO(Hilal): In v2, this API should be cleaned up / torched
  public TapTarget targetCircleColorInt(@ColorInt int color) {
    this.targetCircleColor = color;
    return this;
  }

  /** Specify the color resource for all text **/
  public TapTarget textColor(@ColorRes int color) {
    this.titleTextColorRes = color;
    this.descriptionTextColorRes = color;
    this.buttonTextColorRes = color;
    return this;
  }

  /** Specify the color value for all text **/
  // TODO(Hilal): In v2, this API should be cleaned up / torched
  public TapTarget textColorInt(@ColorInt int color) {
    this.titleTextColor = color;
    this.descriptionTextColor = color;
    this.buttonTextColor = color;
    return this;
  }

  /** Specify the color resource for the title text **/
  public TapTarget titleTextColor(@ColorRes int color) {
    this.titleTextColorRes = color;
    return this;
  }

  /** Specify the color value for the title text **/
  // TODO(Hilal): In v2, this API should be cleaned up / torched
  public TapTarget titleTextColorInt(@ColorInt int color) {
    this.titleTextColor = color;
    return this;
  }

  /** Specify the color resource for the description text **/
  public TapTarget descriptionTextColor(@ColorRes int color) {
    this.descriptionTextColorRes = color;
    return this;
  }

  /** Specify the color value for the description text **/
  // TODO(Hilal): In v2, this API should be cleaned up / torched
  public TapTarget descriptionTextColorInt(@ColorInt int color) {
    this.descriptionTextColor = color;
    return this;
  }

  /** Specify the color resource for the button text text **/
  public TapTarget buttonTextColor(@ColorRes int color) {
    this.buttonTextColorRes = color;
    return this;
  }

  /** Specify the color value for the button text text **/
  // TODO(Hilal): In v2, this API should be cleaned up / torched
  public TapTarget buttonTextColorInt(@ColorInt int color) {
    this.buttonTextColor = color;
    return this;
  }

  /** Specify the color resource for the button text text **/
  public TapTarget buttonColor(@ColorRes int color) {
    this.buttonColorRes = color;
    return this;
  }

  /** Specify the color value for the button text text **/
  // TODO(Hilal): In v2, this API should be cleaned up / torched
  public TapTarget buttonColorInt(@ColorInt int color) {
    this.buttonColor = color;
    return this;
  }

  /** Specify the typeface for all text **/
  public TapTarget textTypeface(Typeface typeface) {
    if (typeface == null) throw new IllegalArgumentException("Cannot use a null typeface");
    titleTypeface = typeface;
    descriptionTypeface = typeface;
    buttonTextTypeface = typeface;
    return this;
  }

  /** Specify the typeface for title text **/
  public TapTarget titleTypeface(Typeface titleTypeface) {
    if (titleTypeface == null) throw new IllegalArgumentException("Cannot use a null typeface");
    this.titleTypeface = titleTypeface;
    return this;
  }

  /** Specify the typeface for description text **/
  public TapTarget descriptionTypeface(Typeface descriptionTypeface) {
    if (descriptionTypeface == null) throw new IllegalArgumentException("Cannot use a null typeface");
    this.descriptionTypeface = descriptionTypeface;
    return this;
  }

  /** Specify the typeface for button text text **/
  public TapTarget buttonTextTypeface(Typeface buttonTextTypeface) {
    if (buttonTextTypeface == null) throw new IllegalArgumentException("Cannot use a null typeface");
    this.buttonTextTypeface = buttonTextTypeface;
    return this;
  }

  /** Specify the text size for the title in SP **/
  public TapTarget titleTextSize(int sp) {
    if (sp < 0) throw new IllegalArgumentException("Given negative text size");
    this.titleTextSize = sp;
    return this;
  }

  /** Specify the text size for the description in SP **/
  public TapTarget descriptionTextSize(int sp) {
    if (sp < 0) throw new IllegalArgumentException("Given negative text size");
    this.descriptionTextSize = sp;
    return this;
  }

  /** Specify the text size for the button text in SP **/
  public TapTarget buttonTextSize(int sp) {
    if (sp < 0) throw new IllegalArgumentException("Given negative text size");
    this.buttonTextSize = sp;
    return this;
  }

  /** Specify the text size for the button corner radius **/
  public TapTarget buttonCornerRadius(int radius) {
    if (radius < 0) throw new IllegalArgumentException("Given negative radius");
    this.buttonCornersRadius = radius;
    return this;
  }

  public TapTarget buttonVerticalPadding(int dp) {
    if (dp < 0) throw new IllegalArgumentException("Given negative dp");
    this.buttonVerticalPadding = dp;
    return this;
  }

  public TapTarget buttonHorizontalPadding(int dp) {
    if (dp < 0) throw new IllegalArgumentException("Given negative dp");
    this.buttonHorizontalPadding = dp;
    return this;
  }

  /**
   * Specify the text size for the title via a dimen resource
   * <p>
   * Note: If set, this value will take precedence over the specified sp size
   */
  public TapTarget titleTextDimen(@DimenRes int dimen) {
    this.titleTextDimen = dimen;
    return this;
  }

  /** Specify the alpha value [0.0, 1.0] of the description text **/
  public TapTarget descriptionTextAlpha(float descriptionTextAlpha) {
    if (descriptionTextAlpha < 0 || descriptionTextAlpha > 1f) {
      throw new IllegalArgumentException("Given an invalid alpha value: " + descriptionTextAlpha);
    }
    this.descriptionTextAlpha = descriptionTextAlpha;
    return this;
  }

  /**
   * Specify the text size for the description via a dimen resource
   * <p>
   * Note: If set, this value will take precedence over the specified sp size
   */
  public TapTarget descriptionTextDimen(@DimenRes int dimen) {
    this.descriptionTextDimen = dimen;
    return this;
  }

  /**
   * Specify the text size for the button text via a dimen resource
   * <p>
   * Note: If set, this value will take precedence over the specified sp size
   */
  public TapTarget buttonTextDimen(@DimenRes int dimen) {
    this.buttonTextDimen = dimen;
    return this;
  }

  /**
   * Specify the text size for the button corner radius via a dimen resource
   * <p>
   * Note: If set, this value will take precedence over the specified sp size
   */
  public TapTarget buttonCornerRadiusDimen(@DimenRes int dimen) {
    this.buttonCornersRadiusDimen = dimen;
    return this;
  }

  public TapTarget buttonVerticalPaddingDimen(@DimenRes int dimen) {
    this.buttonVerticalPaddingDimen = dimen;
    return this;
  }

  public TapTarget buttonHorizontalPaddingDimen(@DimenRes int dimen) {
    this.buttonHorizontalPaddingDimen = dimen;
    return this;
  }

  /**
   * Specify the color resource to use as a dim effect
   * <p>
   * <b>Note:</b> The given color will have its opacity modified to 30% automatically
   */
  public TapTarget dimColor(@ColorRes int color) {
    this.dimColorRes = color;
    return this;
  }

  /**
   * Specify the color value to use as a dim effect
   * <p>
   * <b>Note:</b> The given color will have its opacity modified to 30% automatically
   */
  // TODO(Hilal): In v2, this API should be cleaned up / torched
  public TapTarget dimColorInt(@ColorInt int color) {
    this.dimColor = color;
    return this;
  }

  /** Specify whether or not to draw a drop shadow around the outer circle **/
  public TapTarget drawShadow(boolean draw) {
    this.drawShadow = draw;
    return this;
  }

  /** Specify whether or not the target should be cancelable **/
  public TapTarget cancelable(boolean status) {
    this.cancelable = status;
    return this;
  }

  /** Specify whether to tint the target's icon with the outer circle's color **/
  public TapTarget tintTarget(boolean tint) {
    this.tintTarget = tint;
    return this;
  }

  /** Specify the icon that will be drawn in the center of the target bounds **/
  public TapTarget icon(Drawable icon) {
    return icon(icon, false);
  }

  public TapTarget targetArrowDrawable(Drawable arrowDrawable) {
    if (arrowDrawable == null) throw new IllegalArgumentException("Cannot use null drawable");
    this.targetArrowDrawable = arrowDrawable;
    this.targetArrowPadding = null;
    this.targetArrowBias = 0.4f;
    return this;
  }

  public TapTarget targetArrowDrawable(Drawable arrowDrawable, Integer padding) {
    if (arrowDrawable == null) throw new IllegalArgumentException("Cannot use null drawable");
    this.targetArrowDrawable = arrowDrawable;
    this.targetArrowPadding = padding;
    this.targetArrowBias = 0.4f;
    return this;
  }

  public TapTarget targetArrowDrawable(Drawable arrowDrawable, Float bias) {
    if (arrowDrawable == null) throw new IllegalArgumentException("Cannot use null drawable");
    if (bias < 0.0f || bias > 1.0f) throw new IllegalArgumentException("Require bias > 0.0f && bias < 1.0f");
    this.targetArrowDrawable = arrowDrawable;
    this.targetArrowPadding = null;
    this.targetArrowBias = bias;
    return this;
  }

  public TapTarget targetArrowDrawable(Drawable arrowDrawable, Integer padding, Float bias) {
    if (arrowDrawable == null) throw new IllegalArgumentException("Cannot use null drawable");
    if (bias < 0.0f || bias > 1.0f) throw new IllegalArgumentException("Require bias > 0.0f && bias < 1.0f");
    this.targetArrowDrawable = arrowDrawable;
    this.targetArrowPadding = padding;
    this.targetArrowBias = bias;
    return this;
  }

  /**
   * Specify the icon that will be drawn in the center of the target bounds
   * @param hasSetBounds Whether the drawable already has its bounds correctly set. If the
   *                     drawable does not have its bounds set, then the following bounds will
   *                     be applied: <br/>
   *                      <code>(0, 0, intrinsic-width, intrinsic-height)</code>
   */
  public TapTarget icon(Drawable icon, boolean hasSetBounds) {
    if (icon == null) throw new IllegalArgumentException("Cannot use null drawable");
    this.icon = icon;

    if (!hasSetBounds) {
      this.icon.setBounds(new Rect(0, 0, this.icon.getIntrinsicWidth(), this.icon.getIntrinsicHeight()));
    }

    return this;
  }

  /** Specify a unique identifier for this target. **/
  public TapTarget id(int id) {
    this.id = id;
    return this;
  }

  /** Specify the target radius in dp. **/
  public TapTarget targetRadius(int targetRadius) {
    this.targetRadius = targetRadius;
    return this;
  }

  /** Return the id associated with this tap target **/
  public int id() {
    return id;
  }

  /**
   * In case your target needs time to be ready (laid out in your view, not created, etc), the
   * runnable passed here will be invoked when the target is ready.
   */
  public void onReady(Runnable runnable) {
    runnable.run();
  }

  /**
   * Returns the target bounds. Throws an exception if they are not set
   * (target may not be ready)
   * <p>
   * This will only be called internally when {@link #onReady(Runnable)} invokes its runnable
   */
  public Rect bounds() {
    if (bounds == null) {
      throw new IllegalStateException("Requesting bounds that are not set! Make sure your target is ready");
    }
    return bounds;
  }

  @Nullable
  Integer outerCircleColorInt(Context context) {
    return colorResOrInt(context, outerCircleColor, outerCircleColorRes);
  }

  @Nullable
  Integer targetCircleColorInt(Context context) {
    return colorResOrInt(context, targetCircleColor, targetCircleColorRes);
  }

  @Nullable
  Integer dimColorInt(Context context) {
    return colorResOrInt(context, dimColor, dimColorRes);
  }

  @Nullable
  Integer titleTextColorInt(Context context) {
    return colorResOrInt(context, titleTextColor, titleTextColorRes);
  }

  @Nullable
  Integer descriptionTextColorInt(Context context) {
    return colorResOrInt(context, descriptionTextColor, descriptionTextColorRes);
  }

  @Nullable
  Integer buttonTextColorInt(Context context) {
    return colorResOrInt(context, buttonTextColor, buttonTextColorRes);
  }

  @Nullable
  Integer buttonColorInt(Context context) {
    return colorResOrInt(context, buttonColor, buttonColorRes);
  }

  int titleTextSizePx(Context context) {
    return dimenOrSize(context, titleTextSize, titleTextDimen);
  }

  int descriptionTextSizePx(Context context) {
    return dimenOrSize(context, descriptionTextSize, descriptionTextDimen);
  }

  int buttonTextSizePx(Context context) {
    return dimenOrSize(context, buttonTextSize, buttonTextDimen);
  }

  int buttonCornersRadius(Context context) {
    return dimenOrSize(context, buttonCornersRadius, buttonCornersRadiusDimen);
  }

  int buttonVerticalPadding(Context context) {
    return dimenOrSize(context, buttonVerticalPadding, buttonVerticalPaddingDimen);
  }

  int buttonHorizontalPadding(Context context) {
    return dimenOrSize(context, buttonHorizontalPadding, buttonHorizontalPaddingDimen);
  }

  @Nullable
  private Integer colorResOrInt(Context context, @Nullable Integer value, @ColorRes int resource) {
    if (resource != -1) {
      return ContextCompat.getColor(context, resource);
    }

    return value;
  }

  private int dimenOrSize(Context context, int size, @DimenRes int dimen) {
    if (dimen != -1) {
      return context.getResources().getDimensionPixelSize(dimen);
    }

    return UiUtil.sp(context, size);
  }
}
