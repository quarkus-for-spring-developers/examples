package org.acme;

public class Output {

  private String input;
  private String operation;
  private String output;
  private String error;

  public String getInput() {
    return this.input;
  }

  public void setInput(String input) {
    this.input = input;
  }

  public String getOperation() {
    return this.operation;
  }

  public void setOperation(String operation) {
    this.operation = operation;
  }

  public String getOutput() {
    return this.output;
  }

  public void setOutput(String output) {
    this.output = output;
  }

  public String getError() {
    return this.error;
  }

  public void setError(String error) {
    this.error = error;
  }

  @Override
  public String toString() {
    return "Output{" +
        "input='" + input + '\'' +
        ", operation='" + operation + '\'' +
        ", output='" + output + '\'' +
        ", error='" + error + '\'' +
        '}';
  }
}
