[![Build Check](https://github.com/ivangarzab/fetching/actions/workflows/build-n-test.yml/badge.svg?branch=main)](https://github.com/ivangarzab/fetching/actions/workflows/build-n-test.yml)

# Fetch Rewards Coding Exercise: Software Engineering - Mobile

The purpose of this repository is to create a simple app that fetches the contents of [this data set](https://hiring.fetch.com/hiring.json), and displays them in a list.

The list items should be grouped by their `listId` field, and sorted by their `name` field.

Moreover, the list should not display any items that have a `null` or blank `name` field in the final result.

## Details (test2)

The project's min requirements must've taken me about 3 hours and a half to complete more or less, and I used an extra hour to create a decent (but not exhaustive) test suit for it.  

Add another half hour or so setting up the GitHub project and getting this README document done, it totals to roughly 5 hours spend for this project at most.

There is also a `build-n-test.yml` GitHub Action that builds the app and runs the test suit for CI/CD checks.

## Architecture

I'm using a clean, unidirectional architecture, with UI/Domain/Data layers. 

This architecture is similar to [Android's recommended architecture](https://developer.android.com/topic/architecture#recommended-app-arch).

Moreover, I'm using Koin for dependency injection, as to facilitate the discovery and acquisition of dependencies throughout the project.

## Trade-Offs

For the purpose of time, I skipped or avoided doing the following:

1. I did not introduce `git` into the project until the very end, so the git history does not represent the way I work normally.
2. As it was already mentioned, I did not create an exhaustive test suit, but only tested the "easy" stuff.
3. The single Composable screen could be broken down further, but I decided to keep it all in one file as to keep it simple.
4. The repository I'm using for this project is also very bare-bones, and would require a distinction between Local & Remote data sources for a production app.

There might be more oversights that I failed to list here, and I'm looking forward to discuss them all in the next step of the interview process.

## Final Notes

I hope you'll consider my candidacy, and I'm looking forward to talk about my solution at any time!

Thank you for your time, and let's keep the conversation going ✌️

-- _[Iván Garza Bermea](https://github.com/ivangarzab)_