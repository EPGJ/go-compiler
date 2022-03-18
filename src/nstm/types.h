
#ifndef TYPES_H
#define TYPES_H

typedef enum {
    INT_TYPE,
    FLOAT32_TYPE,
    BOOL_TYPE,
    STRING_TYPE,
    NO_TYPE, // Used when we need to pass a non-existing type to a function.
} Type;

const char* get_text(Type type);

#endif // TYPES_H

