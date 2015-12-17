package com.codeaffine.eclipse.swt.layout;

import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;

public class FillLayouts {

  private final FillLayout fillLayout;

  public static FillLayouts applyFillLayoutTo( Composite composite ) {
    FillLayout fillLayout = new FillLayout();
    composite.setLayout( fillLayout );
    return new FillLayouts( fillLayout );
  }

  public FillLayouts withSpacing( int spacing ) {
    fillLayout.spacing = spacing;
    return this;
  }

  public FillLayouts withType( int type ) {
    fillLayout.type = type;
    return this;
  }

  public FillLayouts withMargin( int margin ) {
    return withMargin( margin, margin );
  }

  public FillLayouts withMargin( int marginWidth, int marginHeight ) {
    withMarginWidth( marginWidth );
    withMarginHeight( marginHeight );
    return this;
  }

  public FillLayouts withMarginWidth( int marginWidth ) {
    fillLayout.marginWidth = marginWidth;
    return this;
  }

  public FillLayouts withMarginHeight( int marginHeight ) {
    fillLayout.marginHeight = marginHeight;
    return this;
  }

  private FillLayouts( FillLayout fillLayout ) {
    this.fillLayout = fillLayout;
  }
}