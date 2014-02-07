keyboard-mouse
==============

KeyBoard Controlled Mouse.. so that one can operate the mouse simply using keyboard control. Cross Platform


## Ideal Use Case:
1. By pressing a pre-set global shortcut, one is able to move the mouse, fast/slow
2. By pressing another set of key, one is able to perform the mouse's left/middle/right button's click

## How to run:
`mvn exec:java -Dexec.mainClass="me.songyy.App"`

## How To Use:
Cmd + Shift + `key stroke below`:
- H / J / K / L: following the VIM direction, move the mouse to corresponding direction. The movement would speed up if moved towards the same direction, continously.
- U / I: perform the left/right click

## Further Improvement Can Be done:
- hold / release the mouse key (currently only support key-press)
- hold key to indicate move
- quick movement to certain screen area (drawing upon screen needed)
- the global shortcut key needs to change somehow -- they're conflicting with the the left click of the mouse. E.g., in Mac, ctrl + left-click = right-click; shift + left-click = multiple-selection. This is causing unwanted behaviour since I have to use the shift-key for the shortcut.

## Thanks to: 
- [jkeymaster](https://github.com/tulskiy/jkeymaster.git)

