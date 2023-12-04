import tkinter as tk
from tkinter import filedialog
from PIL import Image, ImageTk

def hide_message():
    cover_path = cover_path_var.get()
    message = message_entry.get()

    print("DEBUG: cover_path:", cover_path)
    print("DEBUG: message:", message)

    if not cover_path or not message:
        print("DEBUG: Inside if condition")
        result_label.config(text="Please select an image and enter a message.")
        return

    try:
        print("DEBUG: Before opening cover image")
        cover_image = Image.open(cover_path)
        print("DEBUG: After opening cover image")
        stego_image = hide_text(cover_image, message)

        save_path = filedialog.asksaveasfilename(defaultextension=".png", filetypes=[("PNG files", "*.png")])
        if save_path:
            stego_image.save(save_path)
            result_label.config(text=f"Message hidden successfully in {save_path}")
        else:
            result_label.config(text="Operation canceled.")
    except Exception as e:
        result_label.config(text=f"Error: {str(e)}")



def hide_text(cover_image, message):
    binary_message = ''.join(format(ord(char), '08b') for char in message)
    binary_message += '1111111111111110'  # Add a delimiter to mark the end of the message

    data_index = 0
    cover_pixels = list(cover_image.getdata())
    
    # Ensure the binary message fits within the cover image
    if len(binary_message) > len(cover_pixels) * 3:
        raise ValueError("Message is too long for the given cover image.")

    stego_pixels = []

    for pixel in cover_pixels:
        new_pixel = list(pixel)

        for j in range(3):  # Loop through RGB channels
            if data_index < len(binary_message):
                new_pixel[j] = int(format(new_pixel[j], '08b')[:-1] + binary_message[data_index], 2)
                data_index += 1

        stego_pixels.append(tuple(new_pixel))

    stego_image = Image.new(cover_image.mode, cover_image.size)
    stego_image.putdata(stego_pixels)

    return stego_image




def decode_text(stego_image_path, start_pixel=0, chunk_size=1000):
    try:
        global decoding_in_progress
        stego_image = Image.open(stego_image_path)
        binary_message = ''

        stego_pixels = list(stego_image.getdata())
        pixel_count = len(stego_pixels)

        for i in range(start_pixel, min(start_pixel + chunk_size, pixel_count)):
            pixel = stego_pixels[i]
            for value in pixel:
                binary_message += format(value, '08b')[-1]

        # Find the delimiter '1111111111111110' to mark the end of the message
        delimiter_index = binary_message.find('1111111111111110')
        binary_message = binary_message[:delimiter_index]

        # Convert binary message to ASCII
        message = ''.join(chr(int(binary_message[i:i + 8], 2)) for i in range(0, len(binary_message), 8))

        # Update GUI with the current progress
        result_label.config(text=f"Decoding in progress... ({start_pixel}/{pixel_count})")

        # If there are more pixels to process, schedule the next chunk
        if start_pixel + chunk_size < pixel_count and decoding_in_progress:
            root.after(1, decode_text, stego_image_path, start_pixel + chunk_size)
        else:
            # Update GUI with the final result
            result_label.config(text=f"Decoded Message: {message}")

        return message
    except Exception as e:
        return f"Error decoding: {str(e)}"


def decode_message():
    global decoding_in_progress
    decoding_in_progress = True

    stego_path = stego_path_var.get()

    if not stego_path:
        result_label.config(text="Please select a steganographic image.")
        return

    # Reset result label
    result_label.config(text="")

    # Start decoding process
    root.after(1, decode_text, stego_path)



def browse_image():
    file_path = filedialog.askopenfilename(filetypes=[("Image files", "*.png;*.jpg;*.jpeg;*.gif")])
    if file_path:
        stego_path_var.set(file_path)  # Update stego_path_var directly with file path
        display_image()

def display_image():
    stego_path = stego_path_var.get()
    if stego_path:
        image = Image.open(stego_path)
        image.thumbnail((300, 300))
        imgtk = ImageTk.PhotoImage(image=image)
        image_label.config(image=imgtk)
        image_label.image = imgtk

# GUI setup
root = tk.Tk()
root.title("LSB Steganography")

cover_path_var = tk.StringVar()
stego_path_var = tk.StringVar()

image_label = tk.Label(root)
message_label = tk.Label(root, text="Enter Message:")
message_entry = tk.Entry(root, width=40)
browse_button = tk.Button(root, text="Browse Image", command=browse_image)
hide_button = tk.Button(root, text="Hide Message", command=hide_message)
decode_button = tk.Button(root, text="Decode Message", command=decode_message)
result_label = tk.Label(root, text="", font=("Helvetica", 10), wraplength=300)

message_label.grid(row=1, column=0, pady=5)
message_entry.grid(row=1, column=1, pady=5)
browse_button.grid(row=1, column=2, pady=5)
hide_button.grid(row=2, column=0, columnspan=3, pady=10)
decode_button.grid(row=3, column=0, columnspan=3, pady=10)
result_label.grid(row=4, column=0, columnspan=3, pady=10)
image_label.grid(row=0, column=0, columnspan=3, pady=10)

root.mainloop()
