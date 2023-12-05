# Import Libraries

from tkinter import *
import customtkinter


# Pemilihan Tema

theme = 'dark'
customtkinter.set_appearance_mode(theme)
customtkinter.set_default_color_theme('green')


# Pembuatan Window

root = customtkinter.CTk()
root.geometry('850x400')
root.title('Caesar Cipher En/Decryptor')
root.resizable(False, False)
root.iconbitmap('resource/icon/programIcon.ico')


#^ Function Pengubahan Tema

def themeChange():
    global theme
    if theme == 'dark':
        theme = 'light'
        themeButtonIcon = PhotoImage(file='resource/sun.png')
    elif theme == 'light':
        theme = 'dark'
        themeButtonIcon = PhotoImage(file='resource/moon.png')
    
    customtkinter.set_appearance_mode(theme)
    themeButton.configure(image=themeButtonIcon)


#^ Function Enkripsi/Dekripsi

def cipher():
    jumlahGeser = int(inputKunci.get())
    if jumlahGeser >= 26:
        jumlahGeser -= 26
    if jumlahGeser < 0:
        jumlahGeser = 0

    inputText = inputBox.get('0.0', 'end-1c')

    result = ''
    if pilihan.get() == 'Enkripsi':
        for i in range(len(inputText)):
            char = inputText[i]
            if char.isalpha(): 
                if char.isupper():
                    result += chr((ord(char) + jumlahGeser - 65) % 26 + 65)
                else:
                    result += chr((ord(char) + jumlahGeser - 97) % 26 + 97)
            else:
                result += char
    elif pilihan.get() == 'Dekripsi':
        for i in range(len(inputText)):
            char = inputText[i]
            if char.isalpha(): 
                if char.isupper():
                    result += chr((ord(char) - jumlahGeser - 65) % 26 + 65)
                else:
                    result += chr((ord(char) - jumlahGeser - 97) % 26 + 97)
            else:
                result += char

    outputBox.configure(state='normal')
    outputBox.delete('1.0', 'end')
    outputBox.insert('1.0', result)
    outputBox.configure(state='disabled')


#^ Function Copy ke Clipboard

def copy():
    output_text = outputBox.get('0.0', 'end-1c')

    root.clipboard_clear()
    root.clipboard_append(output_text)
    root.update()


#^ Function Delete Output Box

def delete():
    outputBox.configure(state='normal')
    outputBox.delete('1.0', 'end')
    outputBox.configure(state='disabled')


#^ Function Copy ke Kiri

def copyToLeft():
    output_text = outputBox.get('0.0', 'end-1c')
    inputBox.delete('1.0', 'end')
    inputBox.insert('1.0', output_text)


#? Pembuatan Pengubahan Tema Button

themeButtonIcon = PhotoImage(file='resource/moon.png')
themeButton = customtkinter.CTkButton(
    root,
    text='',
    corner_radius=0,
    image=themeButtonIcon,
    width=41,
    height=100,
    command=themeChange
)
themeButton.place(x=800, y=10)


#* Frame Input

# Pembuatan Frame  

inputFrame = customtkinter.CTkFrame(
    root,
    width=385,
    height=380,
    corner_radius=0
)
inputFrame.place(x=10, y=10)


# Pembuatan Teks 'Plainteks'

inputText = customtkinter.CTkLabel(
    inputFrame,
    text="Plainteks",
    font=customtkinter.CTkFont(size=16, weight="bold")
)
inputText.place(x=15, y=0)


# Pembuatan Input Box

inputBox = customtkinter.CTkTextbox(
    inputFrame,
    width=350,
    height=305,
    corner_radius=0,
    font=customtkinter.CTkFont(size=14),
    wrap='word',
    activate_scrollbars=True,
)
inputBox.place(x=15, y=30)


# Pembuatan Combo Box Pilihan

pilihan = customtkinter.StringVar(value='Enkripsi')
inputPilihan = customtkinter.CTkComboBox(
    inputFrame,
    values=['Enkripsi', 'Dekripsi'],
    variable=pilihan,
    width=148,
    height=31,
    corner_radius=0
)
inputPilihan.place(x=15, y=344)


# Pembuatan Entry Pergeseran

inputKunci = customtkinter.CTkEntry(
    inputFrame,
    width=80,
    height=28,
    font=customtkinter.CTkFont(size=12),
    border_width=0,
    corner_radius=5,
    placeholder_text='0-26'
)
inputKunci.insert('1', '0')
inputKunci.place(x=175, y=345)


# Pembuatan Input Button

inputButtonIcon = PhotoImage(file='resource/play.png')
inputButton = customtkinter.CTkButton(
    inputFrame,
    text='',
    image=inputButtonIcon,
    width=100,
    height=31,
    corner_radius=30,
    command=cipher 
)
inputButton.place(x=265, y=344)


#& Frame Output

# Pembuatan Frame

outputFrame = customtkinter.CTkFrame(
    root,
    width=385,
    height=380,
    corner_radius=0
)
outputFrame.place(x=405, y=10)


# Pembuatan Teks 'Cipherteks'

outputText = customtkinter.CTkLabel(
    outputFrame,
    text="Cipherteks",
    font=customtkinter.CTkFont(size=16, weight="bold")
)
outputText.place(x=15, y=0)


# Pembuatan Output Box

outputBox = customtkinter.CTkTextbox(
    outputFrame,
    width=350,
    height=305,
    corner_radius=0,
    font=customtkinter.CTkFont(size=14),
    wrap='word',
    activate_scrollbars=True,
    state='disabled'
)
outputBox.place(x=15, y=30)


# Pembuatan Copy Button

outputCopyButtonIcon = PhotoImage(file='resource/copy.png')
outputCopyButton = customtkinter.CTkButton(
    outputFrame,
    text = '',
    image=outputCopyButtonIcon,
    width=100,
    height=31,
    corner_radius=30,
    command=copy,
)
outputCopyButton.place(x=15, y=344)


# Pembuatan 'Copy ke Kiri' Button

outputCopyToLeftButton = customtkinter.CTkButton(
    outputFrame,
    text='Copy ke Kiri',
    width=100,
    height=31,
    corner_radius=30,
    text_color='black',
    command=copyToLeft
)
outputCopyToLeftButton.place(x=140, y=344)


# Pembuatan Delete Output Button

outputDeleteButtonIcon = PhotoImage(file='resource/delete.png')
outputDeleteButton = customtkinter.CTkButton(
    outputFrame,
    text='',
    image=outputDeleteButtonIcon,
    width=100,
    height=31,
    corner_radius=30,
    command=delete,
)
outputDeleteButton.place(x=265, y=344)


# Pembuatan Aplikasi

root.mainloop()
