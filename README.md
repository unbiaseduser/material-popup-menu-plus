# material-popup-menu-plus
Fork of https://github.com/zawadz88/MaterialPopupMenu with more features. I guess.

# Including in your project
Add jitpack.io to your repositories.
```gradle
allprojects {
  repositories {
	  ...
	  maven { url 'https://jitpack.io' }
  }
}
```

```
implementation 'com.github.unbiaseduser:material-popup-menu-plus:1.0'
```

# Usage

## Create a menu

<details>
  <summary>Kotlin</summary>

  ```kotlin
  //use the extension function on View
  myView.popupMenu(requireContext()) {
  //DSL to build a menu here.
  }
  ```
  
</details>

<details>
  <summary>Java</summary>
  
  ```java
  new PopupMenuBuilder(requireContext(), myView)
  //builder methods to create a menu
  .build();
  ```
  
</details>

## Add a section

<details>
  <summary>Kotlin</summary>

  ```kotlin
  //Inside the popupMenu function
  section {
    title = "This is an optional title"
    //Add items here. Of course, there's DSL for items too.
  }
  ```
  
</details>

<details>
  <summary>Java</summary>
  
  ```java
  //Inside an instance of PopupMenuBuilder
  .addSection(new SectionBuilder()
          .setTitle("This is an optional title")
          //Add items here.
          .build())
  ```
  
</details>

## Supported items
- Normal items. They have a optional left-facing icon and a mandatory title. They have the following implementations:
  - "Regular" items like you usually see on stock `PopupMenu`s. They can have another `MaterialPopupMenu` attached to it, which enables multi-level menu navigation. If
  they are setup to do so, they'll have a "nested" icon at the end of them, and will show the attached menu when clicked. Otherwise, they simply behave like what you expect from regular menu items.
  - "Navigate back" items: Special item exclusively used for multi-level menu navigation. One is automatically added to the top of each sub-menu, which, when clicked,
  dismisses the current menu and shows the upper-level menu. They cannot be added by themselves for obvious reasons.
- "Toggle" items. They're an extension of "normal" items which have a `CompoundButton` on their right, which will be toggled when the item is clicked.
You can supply a boolean-returning function object to conditionally prevent toggling the button (return `true` to toggle, `false` to prevent toggling).
They have the following implementations:
  - "Switch" items: Items that have a `Switch`.
  - "Checkbox" items: Items that have a `CheckBox`. Functions identically to the "Switch" item.
  - "Radio button" items: Items that have a `RadioButton`. They must be added into a special item mentioned below. Of course, checking a radio button item will uncheck all
  other items in its group (defined by said special item). They cannot be added by themselves for obvious reasons.
- "Radio group" items: A special item that houses "radio button" items, representing a group of them. They don't have any functionality on their own.
- "Custom" items: An item containing a custom view that you supply. Its on-click functionality is still present, however, it can be disabled. In that case, you have to handle
clicks yourself on your view.

To learn more about their properties, it's best that you go check them out yourself. I will document them in the future, but for now, I'm tired as hell, honestly.

## Miscellaneous
This fork incorporates [this PR](https://github.com/zawadz88/MaterialPopupMenu/pull/75). If you're involved with it and you get a feeling of dejà vu here, then yeah.

## Q&A
Q: Does this library support Jetpack Compose?

A: No. If you want it you can check out https://github.com/saket/cascade.

Q: Do you ever plan to do so?

A: No.

Q: Is this a *hard fork*?

A: Short answer: yes. Long answer: Well, at first I just downloaded the original library code to mess around without preserving git-related stuff. I got much further
than I expected, so I decided to release the fork to the public. I *am* aware that I can just fork the library normally, but it is what it is, and trying to disect my
changes into git commits I make with the same amount of effort I put in as my other projects (aka a lot) will quickly drain my sanity.
