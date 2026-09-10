# UI Test Plan

This file is the executable specification for the `test-ui` project skill.

## Test execution information

- **Start command:** `java -cp out ATHENA`
- **Working directory:** <!-- repository root unless another directory is required -->
- **Java version:** Java 25, when applicable
- **Comparison rules:** Exact match, including line breaks and prompts, unless a test case says otherwise.

## Test cases

### Test case 1: Reject unsupported task category

- **Aim:** Verify that an unsupported command does not create a default task.
- **Inputs:**

  ```text
  meeting tomorrow
  list
  bye
  ```

- **Expected output:**

  ```text
   I dont understand what you want me to do
   Here are the tasks in your list:
  Bye. Hope to see you again soon!
  ```
