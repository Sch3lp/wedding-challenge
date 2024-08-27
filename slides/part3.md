# Part 3

## Recap
Screen that allows choosing Assignee and showing their challenges.
In this part: 
* Persisting user state as cookies
* Different redirect patterns
* Implementing "undo" by expiring cookies

## LiveCoding
### Intro
Start app in debug (for hot-reloading) and show http://localhost:8080, select assignee, refresh page.

Explain plan:

Save selected assignee into a cookie.

Change main screen to extract the assignee from the cookie.

Explore springboot redirect.

Explore htmx redirect.

 
### Persisting user state as cookies

1. Save selected Assignee to a Cookie
2. Switch select-assignee target to "body"
3. Change GET to use the cookie
4. Respond with springboot redirect to the root //htmx follows the redirect
5. Respond with htmx redirect to the root //htmx triggers the redirect (+remove target="body")
6. Refactor to use more concrete cookie (with string, not Assignee)
7. Refactor to extension function on request (returning cookie)
8. Add "undo" button
9. Add "undo-select" endpoint, delete: "expires", "epoch"