Example code for the [Quarkus for Spring Developers](https://red.ht/quarkus-spring-devs) eBook. Code is organized by chapter. Each project is self-contained, meaning there is no parent/child structure amongst projects.

# Versions
Whenever possible, versions of Quarkus and Spring used in these examples are kept up-to-date as much as possible. The frameworks may have evolved since the book's writing and perhaps there is a better/different way to do something than what is shown in the book's code excerpts. The code in this repository will be kept in sync with what is shown in the book so that those examples may be replicated successfully. As new editions of the book are published, examples may be changed to reflect new patterns.

Here is a summary of some of the new features which may affect the examples in the book. The examples in this repo won't be updated to take advantage of these capabilities until a new revision of the book is released.

## Quarkus
- [RESTEasy Reactive - to block or not to block](https://quarkus.io/blog/resteasy-reactive-smart-dispatch/)
    - New features in RESTEasy Reactive allow Quarkus to automatically detect whether a method is blocking or non blocking
    - Starting with Quarkus 2.2, this means that the `@Blocking` annotation used in many of the Quarkus examples is no longer needed. Quarkus will "figure it out" on its own.
- Panache Reactive with Hibernate Reactive
    - The Quarkus reactive examples in chapter 4 using Panache Reactive currently use a custom built class for implementing transaction rollback within tests.
    - Quarkus now includes an `@TestReactiveTransaction` annotation that can automatically rollback transactions within tests, similar to how the `@TestTransaction` annotation works in the Hibernate ORM examples in chapter 4.
- `quarkus.hibernate-orm.database.generation` will default to `drop-and-create` when Dev Services is in use.

# Book Chapter Text
The table below describes the versions of the example snippets used in the book's chapter text:

| Framework | Version |
| --------- | ------- |
| Quarkus   | `2.1.4.Final` |
| Spring Boot | `2.5.4` |

# Examples Repo
The table below describes the versions of the examples in this repo:

| Framework | Version |
| --------- | ------- |
| Quarkus   | `2.4.1.Final` |
| Spring Boot | `2.5.6` |

# Chapter List
- Chapter 1 - Introducing Quarkus (No example code)
- [Chapter 2 - Getting Started with Quarkus](chapter-2/README.md)
- [Chapter 3 - Building RESTful Applications](chapter-3/)
- [Chapter 4 - Persistence](chapter-4/)
- [Chapter 5 - Event Driven Services](chapter-5/)
- [Chapter 6 - Building Applications for the Cloud](chapter-6/)
