package org.acme;

public class Input {

  private String input;

  public String getInput() {
    return this.input;
  }

  public void setInput(String input) {
    this.input = input;
  }

  @Override
  public String toString() {
    return "Input{" +
        "input='" + input + '\'' +
        '}';
  }
}
