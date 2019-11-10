# Introduction

A [`Fragment`](http://developer.android.com/guide/components/fragments.html) is a self-contained component with its own user interface \(UI\) and life-cycle that can be reused in different parts of an app's UI. \(A `Fragment` can also be used without a UI, in order to retain values across configuration changes, but this lesson does not cover that usage.\)

A `Fragment` can be a _static_ part of the UI of an `Activity`, which means that the `Fragment` remains on the screen during the entire life-cycle of the `Activity`. However, the UI of an `Activity` may be more effective if it adds or removes the `Fragment` _dynamically_ while the `Activity` is running.

![Activity vs Fragment Life-Cycle](../.gitbook/assets/image%20%282%29.png)

One example of a dynamic `Fragment` is the [`DatePicker`](https://developer.android.com/reference/android/widget/DatePicker.html) object, which is an instance of [`DialogFragment`](https://developer.android.com/reference/android/support/v4/app/DialogFragment.html), a subclass of [`Fragment`](https://developer.android.com/reference/android/app/Fragment.html). The date picker displays a dialog window floating on top of its `Activity` window when a user taps a button or an action occurs. The user can click **OK** or **Cancel** to close the `Fragment`.

Benefits of using Fragments

* Reuse a Fragment in more than one Activity
* Add or remove dynamically as needed
* Integrate a mini-UI within an Activity
* Retain data instances after a configuration change
* Represent sections of a layout for different screen sizes

 This practical introduces the [`Fragment`](https://developer.android.com/reference/android/app/Fragment.html) class and shows you how to include a `Fragment` as a static part of a UI, as well as how to use `Fragment` transactions to add, replace, or remove a `Fragment` dynamically.

### What you'll do

* Create a `Fragment` to use as a UI element that gives users a "yes" or "no" choice.
* Add interactive elements to the `Fragment` that enable the user to choose "yes" or "no".
* Include the `Fragment` for the duration of an `Activity`.
* Use `Fragment` transactions to add, replace, and remove a `Fragment` while an `Activity` is running.

