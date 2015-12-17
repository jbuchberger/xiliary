package com.codeaffine.eclipse.swt.widget.scrollable.context;

import static com.codeaffine.eclipse.swt.util.ControlReflectionUtil.$;
import static java.util.stream.Collectors.toList;

import java.util.stream.Stream;

import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.custom.ViewForm;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Scrollable;

import com.codeaffine.eclipse.swt.util.ControlReflectionUtil;

class LayoutReconciliation {

  private final ScrollableControl<? extends Scrollable> scrollable;
  private final ControlReflectionUtil controlReflectionUtil;
  private final Composite adapter;

  LayoutReconciliation( Composite adapter, ScrollableControl<? extends Scrollable> scrollable ) {
    this.controlReflectionUtil = new ControlReflectionUtil();
    this.scrollable = scrollable;
    this.adapter = adapter;
  }

  void run() {
    if( adapter.getParent() != null ) {
      if( adapter.getParent().getLayout() instanceof StackLayout ) {
        reconcileStackLayout();
      }
      if( adapter.getParent() instanceof ViewForm ) {
        reconcileViewFormLayout();
      }
      if( adapter.getParent().getClass().getName().equals( "org.eclipse.ui.part.PageBook" ) ) {
        reconcilePageBookLayout();
      }
      if( adapter.getParent() instanceof SashForm ) {
        reconcileSashLayout();
      }
      if( adapter.getParent() instanceof CTabFolder ) {
        reconcileCTabFolder();
      }
    }
  }

  private void reconcileStackLayout() {
    StackLayout stackLayout = ( StackLayout )adapter.getParent().getLayout();
    if( scrollable.isSameAs( stackLayout.topControl ) ) {
      stackLayout.topControl = adapter;
      adapter.getParent().layout();
    }
  }

  private void reconcileViewFormLayout() {
    ViewForm viewForm = ( ViewForm )adapter.getParent();
    if( scrollable.isSameAs( viewForm.getContent() ) ) {
      viewForm.setContent( adapter );
      viewForm.layout();
    }
    if( scrollable.isSameAs( viewForm.getTopCenter() ) ) {
      viewForm.setTopCenter( adapter );
      viewForm.layout();
    }
    if( scrollable.isSameAs( viewForm.getTopLeft() ) ) {
      viewForm.setTopLeft( adapter );
      viewForm.layout();
    }
    if( scrollable.isSameAs( viewForm.getTopRight() ) ) {
      viewForm.setTopRight( adapter );
      viewForm.layout();
    }
  }

  private void reconcilePageBookLayout() {
    Composite parent = adapter.getParent();
    if( scrollable.isSameAs( controlReflectionUtil.getField( parent, "currentPage", Control.class ) ) ) {
      controlReflectionUtil.invoke( parent, "showPage", $( adapter, Control.class ) );
      adapter.getParent().layout();
    }
  }

  private void reconcileSashLayout() {
    SashForm parent = ( SashForm )adapter.getParent();
    if( needsReparenting( parent ) ) {
      scrollable.setParent( adapter );
      controlReflectionUtil.setField( scrollable.getControl(), "parent", adapter.getParent() );
      adapter.getParent().layout();
    }
    if( scrollable.isSameAs( controlReflectionUtil.getField( parent, "maxControl", Control.class ) ) ) {
      controlReflectionUtil.setField( parent, "maxControl", adapter );
      adapter.getParent().layout();
    }
  }

  private void reconcileCTabFolder() {
    CTabFolder parent = ( CTabFolder )adapter.getParent();
    Stream.of( parent.getItems() ).forEach( item -> reconcileCItemScrollable( item ) );
  }

  private void reconcileCItemScrollable( CTabItem item ) {
    if( scrollable.isSameAs( item.getControl() ) ) {
      item.setControl( adapter );
      adapter.getParent().layout();
    }
  }

  private boolean needsReparenting( Composite parent ) {
    return !Stream.of( parent.getChildren() )
      .filter( control -> scrollable.isSameAs( control ) )
      .collect( toList() )
      .isEmpty();
  }
}