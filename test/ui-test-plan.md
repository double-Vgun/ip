# UI Test Plan

This file is the executable specification for the `test-ui` project skill.

## Test execution information

- **Start command:** `java -cp out ATHENA`
- **Working directory:** <!-- repository root unless another directory is required -->
- **Java version:** Java 25, when applicable
- **Comparison rules:** Compare the response lines shown in each test case exactly. Ignore the greeting banner and
  horizontal separator lines.
- **Preparation:** Delete `data/athena.txt` before each test case if it exists, unless the test case specifies other
  setup.

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
  I dont understand what you want me to do, please start with deadline, todo, event, mark, unmark, list or bye
   Here are the tasks in your list:
  Bye. Hope to see you again soon!
  ```

### Test case 2: Save task-list changes to disk

- **Aim:** Verify that added, marked, and unmarked tasks are saved automatically.
- **Inputs:**

  ```text
  todo read book
  deadline return book /by June 6th
  event project meeting /from Aug 6th 2pm /to Aug 6th 4pm
  mark 1
  unmark 1
  bye
  ```

- **Expected output:**

  ```text
   Got it. I've added this task:
     [T][ ] read book
   Now you have 1 tasks in the list.
   Got it. I've added this task:
     [D][ ] return book (by: June 6th)
   Now you have 2 tasks in the list.
   Got it. I've added this task:
     [E][ ] project meeting (from: Aug 6th 2pm to: Aug 6th 4pm)
   Now you have 3 tasks in the list.
   Nice! I've marked this task as done:
     [T][X] read book
   OK, I've marked this task as not done yet:
     [T][ ] read book
  Bye. Hope to see you again soon!
  ```

- **Expected `data/athena.txt`:**

  ```text
  T | 0 | read book
  D | 0 | return book | June 6th
  E | 0 | project meeting | Aug 6th 2pm | Aug 6th 4pm
  ```

### Test case 3: Load saved tasks on startup

- **Aim:** Verify that saved task types, details, and completion states are restored.
- **Preparation:** Create `data/athena.txt` with the following contents:

  ```text
  T | 1 | read book
  D | 0 | return book | June 6th
  E | 0 | project meeting | Aug 6th 2pm | Aug 6th 4pm
  ```

- **Inputs:**

  ```text
  list
  bye
  ```

- **Expected output:**

  ```text
   Here are the tasks in your list:
   1.[T][X] read book
   2.[D][ ] return book (by: June 6th)
   3.[E][ ] project meeting (from: Aug 6th 2pm to: Aug 6th 4pm)
  Bye. Hope to see you again soon!
  ```

### Test case 4: Preserve file-format delimiter characters

- **Aim:** Verify that pipe and backslash characters survive saving and a subsequent startup.
- **First-run inputs:**

  ```text
  todo review A | B \ notes
  bye
  ```

- **Expected first-run output:**

  ```text
   Got it. I've added this task:
     [T][ ] review A | B \ notes
   Now you have 1 tasks in the list.
  Bye. Hope to see you again soon!
  ```

- **Expected `data/athena.txt`:**

  ```text
  T | 0 | review A \| B \\ notes
  ```

- **Second-run inputs:**

  ```text
  list
  bye
  ```

- **Expected second-run output:**

  ```text
   Here are the tasks in your list:
   1.[T][ ] review A | B \ notes
  Bye. Hope to see you again soon!
  ```

### Test case 5: Reject malformed saved data safely

- **Aim:** Verify that invalid saved data produces a clear startup error instead of a Java exception or data loss.
- **Preparation:** Create `data/athena.txt` with the following contents:

  ```text
  T | maybe | read book
  ```

- **Inputs:** None; the application should stop during startup.
- **Expected output:**

  ```text
  Unable to start ATHENA. Invalid data in data\athena.txt at line 1: completion state must be 0 or 1
  ```

### Test case 6: Reject invalid command arguments safely

- **Aim:** Verify that extra arguments, missing task numbers, oversized numbers, and incomplete task details produce
  clear errors.
- **Inputs:**

  ```text
  list extra
  mark
  mark 999999999999999999999999
  deadline /by June 6th
  deadline return book /by
  event /from 2pm /to 4pm
  event meeting /from /to 4pm
  event meeting /from 2pm /to
  bye
  ```

- **Expected output:**

  ```text
  The list command does not take additional arguments
  Please enter exactly one numeric task number
  Task number is too large
  Please tell me what deadline task to add
  Please add a date for the deadline
  Please tell me what event task to add
  Please add a start time for the event
  Please add an end time for the event
  Bye. Hope to see you again soon!
  ```

### Test case 7: Enforce the task-list capacity

- **Aim:** Verify that adding a 101st task is rejected without altering the 100 tasks already saved.
- **Inputs:** Enter `todo task 1` through `todo task 101`, followed by `bye`.
- **Expected final responses:**

  ```text
   Got it. I've added this task:
     [T][ ] task 100
   Now you have 100 tasks in the list.
  Task list is full. No more tasks can be added
  Bye. Hope to see you again soon!
  ```

- **Expected `data/athena.txt`:** Exactly 100 lines, ending with `T | 0 | task 100`.

### Test case 8: Roll back a task when saving fails

- **Aim:** Verify that a failed save is reported and does not leave the attempted task in memory.
- **Preparation:** Replace the `data` directory with a regular file named `data` so `data/athena.txt` cannot be
  created.
- **Inputs:**

  ```text
  todo read book
  list
  bye
  ```

- **Expected output:**

  ```text
  Unable to save tasks to data\athena.txt
   Here are the tasks in your list:
  Bye. Hope to see you again soon!
  ```

### Test case 9: Start and save without an existing data folder

- **Aim:** Verify that ATHENA starts with an empty list when neither the data file nor its folder exists, then creates
  both when the first task is saved.
- **Preparation:** Delete the `data` folder and everything inside it if it exists.
- **Inputs:**

  ```text
  list
  todo read book
  bye
  ```

- **Expected output:**

  ```text
   Here are the tasks in your list:
   Got it. I've added this task:
     [T][ ] read book
   Now you have 1 tasks in the list.
  Bye. Hope to see you again soon!
  ```

- **Expected result:** The `data` folder is created and `data/athena.txt` contains:

  ```text
  T | 0 | read book
  ```
