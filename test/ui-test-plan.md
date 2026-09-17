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

### Test case 2: Delete a task and renumber the remaining tasks

- **Aim:** Verify that deleting a valid task removes it and keeps the task numbers continuous.
- **Inputs:**

  ```text
  todo first task
  todo second task
  todo third task
  delete 2
  list
  bye
  ```

- **Expected output:**

  ```text
   Noted. I've removed this task:
     [T][ ] second task
   Now you have 2 tasks in the list.
   Here are the tasks in your list:
   1.[T][ ] first task
   2.[T][ ] third task
  Bye. Hope to see you again soon!
  ```

### Test case 3: Reject an invalid delete argument

- **Aim:** Verify that `delete` requires exactly one integer task number.
- **Inputs:**

  ```text
  delete
  delete two
  delete 1 extra
  bye
  ```

- **Expected output:**

  ```text
  Please enter a valid task number after delete
  Please enter a valid task number after delete
  Please enter a valid task number after delete
  Bye. Hope to see you again soon!
  ```

### Test case 4: Reject a non-positive delete number

- **Aim:** Verify that task numbers for `delete` must be greater than zero.
- **Inputs:**

  ```text
  delete 0
  delete -1
  bye
  ```

- **Expected output:**

  ```text
  Task number must be more than 0
  Task number must be more than 0
  Bye. Hope to see you again soon!
  ```

### Test case 5: Reject a task number outside the list

- **Aim:** Verify that `delete` cannot select a task number that does not exist.
- **Inputs:**

  ```text
  todo only task
  delete 2
  bye
  ```

- **Expected output:**

  ```text
  Task number 2 does not exist in the list
  Bye. Hope to see you again soon!
  ```
