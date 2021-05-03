package org.acme.domain;

public class Output {

  private String input;
  private String operation;
  private String output;
  private String error;

  public Output() {
  }

  public Output(String input, String operation, String output, String error) {
    this.input = input;
    this.operation = operation;
    this.output = output;
    this.error = error;
  }

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
        "input='" + this.input + '\'' +
        ", operation='" + this.operation + '\'' +
        ", output='" + this.output + '\'' +
        ", error='" + this.error + '\'' +
        '}';
  }
}
