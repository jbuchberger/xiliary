package com.codeaffine.workflow.persistence;

import com.codeaffine.workflow.NodeLoader;
import com.codeaffine.workflow.WorkflowService;
import com.codeaffine.workflow.definition.VariableDeclaration;
import com.codeaffine.workflow.internal.ScopeContext;
import com.codeaffine.workflow.internal.WorkflowServiceImpl;

public class WorkflowServiceAdapter {

  private final WorkflowServiceImpl workflowService;

  public WorkflowServiceAdapter( WorkflowService workflowService ) {
    this.workflowService = ( WorkflowServiceImpl )workflowService;
  }

  public ClassFinder getClassFinder() {
    return workflowService.getClassFinder();
  }

  public NodeLoader getNodeLoader() {
    return workflowService.getNodeLoader();
  }

  public VariableDeclaration<?>[] getVariableDeclarations() {
    return ( ( ScopeContext )workflowService.getServiceScope() ).getVariableDeclarations();
  }
}
