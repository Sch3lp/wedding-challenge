# Part 2

## Recap
Screen that allows choosing Assignee and showing a welcome message.
In this part: 
* Combining Bulma's Dropdown and htmx's active search into a different assignee selection component, using expand and contract
* Approval Tests before refactoring

## LiveCoding
### Intro
1. Start app in debug (for hot-reloading) and show http://localhost:8080, select assignee

Explain plan:

Show bulma dropdown: https://bulma.io/documentation/components/dropdown/

Show htmx active search: https://htmx.org/examples/active-search/


### Redesigning the assignee selection to use Bulma dropdown with active search
1. Rework select assignee to be a Bulma dropdown, using expand and contract (ass)
2. Add search endpoint (search); order matters!
3. Execute the contract part of expand and contract

### Add component
1. Add approval test
2. Refactor selection into a component (re-run tests)
   1. showAssigneeSelection is a div, so go to DIV
   2. create assigneeSelection factory method
   3. use default class of container
   4. use .visit with a lambda param and copy paste contents of showAssigneeSelection
   5. turn List<Assignee> into FlowContent.() -> Unit
3. turn assignees() into sub component (typesafety)
   1. assignees is a div, so same thing
