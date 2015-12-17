package com.codeaffine.eclipse.ui.swt.theme;

import static org.assertj.core.api.Assertions.assertThat;

import org.eclipse.e4.ui.css.swt.dom.ControlElement;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Scrollable;
import org.eclipse.swt.widgets.Shell;
import org.junit.Rule;
import org.junit.Test;

import com.codeaffine.eclipse.swt.test.util.DisplayHelper;

@SuppressWarnings("restriction")
public class ControlElementsTest {

  @Rule
  public final DisplayHelper displayHelper = new DisplayHelper();

  @Test
  public void extractControl() {
    Shell expected = displayHelper.createShell();

    ControlElement controlElement = new ControlElement( expected, null );
    Control actual = ControlElements.extractControl( controlElement );

    assertThat( actual ).isSameAs( expected );
  }

  @Test
  public void extractScrollable() {
    Shell expected = displayHelper.createShell();

    ControlElement controlElement = new ControlElement( expected, null );
    Scrollable actual = ControlElements.extractScrollable( controlElement );

    assertThat( actual ).isSameAs( expected );
  }

  @Test( expected = ClassCastException.class )
  public void extractControlWithUnsupportedElementType() {
    ControlElements.extractControl( new Object() );
  }

  @Test( expected = ClassCastException.class )
  public void extractScrollableWithUnsupportedElementType() {
    ControlElements.extractScrollable( new Object() );
  }
}