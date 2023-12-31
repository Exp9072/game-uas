import itertools
from tkinter import *
from tkinter import filedialog, messagebox as mb
from PIL import Image
from tkinterdnd2 import DND_FILES


# Creating the basic Python Image Steganography functions
def generate_data(pixels, data):
    # This function will convert the incoming data to 8-bit binary format using its ASCII values and return them
    data_in_binary = []

    for i in data:
        binary_data = format(ord(i), '08b')
        data_in_binary.append(binary_data)

    length_of_data = len(data_in_binary)
    image_data = iter(pixels)

    for a in range(length_of_data):
        pixels = [val for val in image_data.__next__()[:3] + image_data.__next__()[:3] + image_data.__next__()[:3]]

        for b in range(8):
            if (data_in_binary[a][b] == '1') and (pixels[b] % 2 != 0):
                pixels[b] -= 1
            elif (data_in_binary[a][b] == '0') and (pixels[b] % 2 == 0):
                if pixels[b] == 0:
                    pixels[b] += 1
                pixels[b] -= 1

        if (length_of_data-1) == a:
            if pixels[-1] % 2 == 0:
                if pixels[-1] == 0:
                    pixels[-1] += 1
                else:
                    pixels[-1] -= 1

        pixels = tuple(pixels)

        yield pixels[:3]
        yield pixels[3:6]
        yield pixels[6:9]

def encryption(img, data):
    width, height = img.size
    x, y = 0, 0

    for pixel in generate_data(img.getdata(), data):
        img.putpixel((x, y), pixel)
        if x == width - 1:
            x = 0
            y += 1
        else:
            x += 1

    # Add a termination marker at the end of the message
    termination_marker = '###END###'
    for char in termination_marker:
        binary_data = format(ord(char), '08b')
        for bit in binary_data:
            pixel = img.getpixel((x, y))

            if len(pixel) == 4:  # Check if the image has an alpha channel
                r, g, b, a = pixel
                img.putpixel((x, y), (r & 0b11111110, g & 0b11111110, b & 0b11111110, a))  # Clear the LSBs
                img.putpixel((x, y), (r | int(bit), g | int(bit), b | int(bit), a))  # Set the LSBs
            else:
                r, g, b = pixel
                img.putpixel((x, y), (r & 0b11111110, g & 0b11111110, b & 0b11111110))  # Clear the LSBs
                img.putpixel((x, y), (r | int(bit), g | int(bit), b | int(bit)))  # Set the LSBs

            if x == width - 1:
                x = 0
                y += 1
            else:
                x += 1

    return img


def main_decryption(img):
    data = ''
    termination_marker = '###END###'

    # Convert the image to RGB mode if it's not in RGB mode
    if img.mode != 'RGB':
        img = img.convert('RGB')

    pixels = list(itertools.chain.from_iterable(img.getdata()))

    # Find the termination marker
    termination_marker_bits = [int(bit) for bit in ''.join(format(ord(char), '08b') for char in termination_marker)]
    termination_index = -1

    for i in range(len(pixels) - len(termination_marker_bits) * 3):
        # Extract least significant bits of the next 8 pixels for each channel
        lsb_pixels = [
            pixel & 1 for pixel in pixels[i:i + len(termination_marker_bits) * 3:3]
        ]

        # Compare with termination marker bits
        if lsb_pixels == termination_marker_bits:
            termination_index = i
            break

    if termination_index == -1:
        mb.showerror("Error", "Termination marker not found. This image may not contain encoded data.")
        return ''

    # Extract the binary data
    binary_data = pixels[:termination_index]

    # Ensure the binary data is padded to multiples of 8
    padding_length = (8 - len(binary_data) % 8) % 8
    if padding_length > 0:
        mb.showwarning("Warning", f"Padding detected. Data may be corrupted or incomplete.")

    padded_data = binary_data + [0] * padding_length

    # Convert binary data to characters
    for i in range(0, len(padded_data), 8):
        byte = padded_data[i:i + 8]
        byte_str = ''.join(map(str, byte))

        # Check if the binary string has a valid length
        if len(byte_str) == 8:
            data += chr(int(byte_str, 2))
        else:
            print(f"Invalid binary string: {byte_str}")
    print("data = " + data)
    return data
    



def on_drop(event):
    img_path_label.config(text=event.data)


def decode_image():
    print("Decode image function called.")  # Add this line for debugging

    if not img_path_label.cget("text"):
        mb.showerror("Error", "Please select an image file first.")
        return

    img_path = img_path_label.cget("text")
    image = Image.open(img_path, 'r')

    decoded_message = main_decryption(image)
    decoded_message_label.config(text=f"Decoded Message: {decoded_message}")


def encode_image():
    if not img_path_label.cget("text"):
        mb.showerror("Error", "Please select an image file first.")
        return

    img_path = img_path_label.cget("text")
    new_image_name = f"encoded_{img_path.split('/')[-1]}"

    image = Image.open(img_path, 'r')
    new_image = image.copy()

    message = entry_message.get()  # Get the message from the entry widget
    if not message:
        mb.showerror("Error", "Please enter a message to encode.")
        return

    encryption(new_image, message)

    new_image.save(new_image_name, 'png')
    mb.showinfo("Success", f"Image has been encoded and saved as {new_image_name}")
def select_image():
    img_path = filedialog.askopenfilename(title="Select Image File", filetypes=[("Image files", "*.png;*.jpg;*.jpeg")])
    img_path_label.config(text=img_path)

# Initializing the window
root = Tk()
root.title('Steganography')
root.geometry('400x400')  # Larger window size
root.resizable(0, 0)
root.config(bg='NavajoWhite')

Label(root, text='Steganography', font=('Comic Sans MS', 15), bg='NavajoWhite',
      wraplength=380).place(x=10, y=10)  # Adjusted position

# Entry widget for the message
entry_message = Entry(root, width=40)
entry_message.place(x=10, y=180)

# Button to select an image file
Button(root, text='Select Image', width=25, font=('Times New Roman', 13), bg='SteelBlue', command=select_image).place(
    x=10, y=220)  # Adjusted position

# Button to run encoding
Button(root, text='Run Encoding', width=25, font=('Times New Roman', 13), bg='SteelBlue', command=encode_image).place(
    x=210, y=220)  # Adjusted position

# Button to run decoding
Button(root, text='Decode Image', width=25, font=('Times New Roman', 13), bg='SteelBlue', command=decode_image).place(
    x=210, y=260)  # Adjusted position

# Label to display the selected image path
img_path_label = Label(root, text='', bg='NavajoWhite', wraplength=380)
img_path_label.place(x=10, y=60)  # Adjusted position

# Label to display the decoded message
decoded_message_label = Label(root, text='', bg='NavajoWhite', wraplength=380)
decoded_message_label.place(x=10, y=300)  # Adjusted position

# Finalizing the window
root.update()
root.mainloop()