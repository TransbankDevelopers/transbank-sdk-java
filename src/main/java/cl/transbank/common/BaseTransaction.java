package cl.transbank.common;

import cl.transbank.model.Options;

/**
 * This abstract class represents a base transaction with common properties and methods.
 */
public abstract class BaseTransaction {

  /**
   * The options for the transaction.
   */
  protected Options options = null;

  protected BaseTransaction(Options options){
    if (options == null){
      throw new IllegalArgumentException("Options can't be null.");
    }
    this.options = options;
  }
}
