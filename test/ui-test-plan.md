# UI Test Plan

## Test configuration

- Working directory: repository root
- Required JDK: Java 25
- Setup command (PowerShell):

  ```powershell
  $uiTestSources = Get-ChildItem -Path "src/main/java" -Filter "*.java"
  New-Item -ItemType Directory -Force -Path "_temp/ui-test-classes" | Out-Null
  javac -d "_temp/ui-test-classes" $uiTestSources.FullName
  ```

- Launch command (PowerShell):

  ```powershell
  java -cp "_temp/ui-test-classes" Potato
  ```

- Comparison: exact text after normalizing `CRLF` and `LF` line endings; terminal input echo is excluded.

### Expected startup output

```text
____________________________________________________________
 ____   ___    _____     _     _____   ___
|  _ \ / _ \  |_   _|   / \   |_   _| / _ \
| |_) | | | |   | |    / _ \    | |  | | | |
|  __/| |_| |   | |   / ___ \   | |  | |_| |
|_|    \___/    |_|  /_/   \_\  |_|   \___/
Hello! I'm Potato.
What can I do for you?
____________________________________________________________
```

## Test cases

## TC-1: Baseline command workflow

- Aim: Verify all currently supported commands in one stateful workflow: create a to-do, deadline, and event; list tasks; mark and unmark a task; save each change; and exit the application.
- Setup: Ensure `data/potato.txt` does not exist before launching the application.
- Inputs and expected outputs:

  1. Input

     ```text
     todo read book
     ```

     Expected output

     ```text
     ____________________________________________________________
      Got it. I've added this task:
        [T][ ] read book
      Now you have 1 task in the list.
     ____________________________________________________________
     ```

  2. Input

     ```text
     deadline return book /by Sunday
     ```

     Expected output

     ```text
     ____________________________________________________________
      Got it. I've added this task:
        [D][ ] return book (by: Sunday)
      Now you have 2 tasks in the list.
     ____________________________________________________________
     ```

  3. Input

     ```text
     event project meeting /from Monday 2pm /to Monday 3pm
     ```

     Expected output

     ```text
     ____________________________________________________________
      Got it. I've added this task:
        [E][ ] project meeting (from: Monday 2pm to: Monday 3pm)
      Now you have 3 tasks in the list.
     ____________________________________________________________
     ```

  4. Input

     ```text
     list
     ```

     Expected output

     ```text
     ____________________________________________________________
      Here are the tasks in your list:
      1.[T][ ] read book
      2.[D][ ] return book (by: Sunday)
      3.[E][ ] project meeting (from: Monday 2pm to: Monday 3pm)
     ____________________________________________________________
     ```

  5. Input

     ```text
     mark 2
     ```

     Expected output

     ```text
     ____________________________________________________________
      Nice! I've marked this task as done:
        [D][X] return book (by: Sunday)
     ____________________________________________________________
     ```

  6. Input

     ```text
     unmark 2
     ```

     Expected output

     ```text
     ____________________________________________________________
      OK, I've marked this task as not done yet:
        [D][ ] return book (by: Sunday)
     ____________________________________________________________
     ```

  7. Input

     ```text
     bye
     ```

     Expected output

     ```text
     ____________________________________________________________
     Bye. Hope to see you again soon!
     ____________________________________________________________
     ```

- Expected save file after steps 1-3, 5, and 6: `data/potato.txt` exists and contains the complete current task list. After step 6, its exact contents are:

  ```text
  T | 0 | read book
  D | 0 | return book | Sunday
  E | 0 | project meeting | Monday 2pm | Monday 3pm
  ```

## TC-2: Invalid task input recovery

- Aim: Verify malformed task commands are ignored and the application continues accepting input.
- Setup: Ensure `data/potato.txt` does not exist before launching the application.
- Inputs and expected outputs:

  1. Input

     ```text
     todo
     ```

     Expected output

     ```text
     ____________________________________________________________
     Invalid to-do input. Format: todo [description]
     ____________________________________________________________
     ```

  2. Input

     ```text
     deadline return book
     ```

     Expected output

     ```text
     ____________________________________________________________
     Invalid deadline input. Format: deadline [description] /by [date]
     ____________________________________________________________
     ```

  3. Input

     ```text
     event project meeting /from Monday 2pm
     ```

     Expected output

     ```text
     ____________________________________________________________
     Invalid event input. Format: event [description] /from [date] /to [date]
     ____________________________________________________________
     ```

  4. Input

     ```text
     todo read book
     ```

     Expected output

     ```text
     ____________________________________________________________
      Got it. I've added this task:
        [T][ ] read book
      Now you have 1 task in the list.
     ____________________________________________________________
     ```

  5. Input

     ```text
     bye
     ```

     Expected output

     ```text
     ____________________________________________________________
     Bye. Hope to see you again soon!
     ____________________________________________________________
     ```

## TC-3: Load tasks at startup

- Aim: Verify the application loads all supported task types and their completion states from an existing save file.
- Setup: Create `data/potato.txt` with these exact contents before launching the application:

  ```text
  T | 1 | read book
  D | 0 | return book | Sunday
  E | 1 | project meeting | Monday 2pm | Monday 3pm
  ```

- Inputs and expected outputs:

  1. Input

     ```text
     list
     ```

     Expected output

     ```text
     ____________________________________________________________
      Here are the tasks in your list:
      1.[T][X] read book
      2.[D][ ] return book (by: Sunday)
      3.[E][X] project meeting (from: Monday 2pm to: Monday 3pm)
     ____________________________________________________________
     ```

  2. Input

     ```text
     bye
     ```

     Expected output

     ```text
     ____________________________________________________________
     Bye. Hope to see you again soon!
     ____________________________________________________________
     ```
