import uuid

print("Нажмите Enter для генерации нового UUID (Ctrl+C для выхода)")
while True:
    input()
    print(uuid.uuid4())