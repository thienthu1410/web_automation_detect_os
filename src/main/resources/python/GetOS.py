import platform

def get_os_name():
    os_name = platform.system()
    return os_name

if __name__ == "__main__":
    print(get_os_name())