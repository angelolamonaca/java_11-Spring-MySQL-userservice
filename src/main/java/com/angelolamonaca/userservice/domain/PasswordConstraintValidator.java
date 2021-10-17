package com.angelolamonaca.userservice.domain;

import org.passay.*;
import org.passay.dictionary.WordListDictionary;
import org.passay.dictionary.WordLists;
import org.passay.dictionary.sort.ArraysSort;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.io.FileReader;
import java.io.IOException;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author Angelo Lamonaca (https://www.angelolamonaca.com/)
 * @version 1.0
 * @since 17/10/2021
 */
public class PasswordConstraintValidator implements ConstraintValidator<ValidPassword, String> {
    private DictionaryRule dictionaryRule;

    @Override
    public void initialize(ValidPassword constraintAnnotation) {
        try {
            String invalidPasswordList = Objects.requireNonNull(this.getClass().getResource("/invalid-password-list.txt")).getFile();
            dictionaryRule = new DictionaryRule(
                    new WordListDictionary(WordLists.createFromReader(
                            // Reader around the word list file
                            new FileReader[]{
                                    new FileReader(invalidPasswordList)
                            },
                            // True for case sensitivity, false otherwise
                            false,
                            // Dictionaries must be sorted
                            new ArraysSort()
                    )));
        } catch (IOException e) {
            throw new RuntimeException("could not load word list", e);
        }
    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        PasswordValidator validator = new PasswordValidator(Arrays.asList(

                // at least 8 characters
                new LengthRule(8, 30),

                // at least one upper-case character
                new CharacterRule(EnglishCharacterData.UpperCase, 1),

                // at least one lower-case character
                new CharacterRule(EnglishCharacterData.LowerCase, 1),

                // at least one digit character
                new CharacterRule(EnglishCharacterData.Digit, 1),

                // at least one symbol (special character)
                new CharacterRule(EnglishCharacterData.Special, 1),

                // no whitespace
                new WhitespaceRule(),

                // no common passwords
                dictionaryRule
        ));

        RuleResult result = validator.validate(new PasswordData(password));

        if (result.isValid()) {
            return true;
        }

        List<String> messages = validator.getMessages(result);
        String messageTemplate = String.join(",", messages);
        context.buildConstraintViolationWithTemplate(messageTemplate)
                .addConstraintViolation()
                .disableDefaultConstraintViolation();
        return false;
    }

    public List<String> validate(String password) {
        try {
            String invalidPasswordList = Objects.requireNonNull(this.getClass().getResource("/invalid-password-list.txt")).getFile();
            dictionaryRule = new DictionaryRule(
                    new WordListDictionary(WordLists.createFromReader(
                            // Reader around the word list file
                            new FileReader[]{
                                    new FileReader(invalidPasswordList)
                            },
                            // True for case sensitivity, false otherwise
                            false,
                            // Dictionaries must be sorted
                            new ArraysSort()
                    )));
        } catch (IOException e) {
            throw new RuntimeException("could not load word list", e);
        }
        PasswordValidator validator = new PasswordValidator(Arrays.asList(

                // at least 8 characters
                new LengthRule(8, 30),

                // at least one upper-case character
                new CharacterRule(EnglishCharacterData.UpperCase, 1),

                // at least one lower-case character
                new CharacterRule(EnglishCharacterData.LowerCase, 1),

                // at least one digit character
                new CharacterRule(EnglishCharacterData.Digit, 1),

                // at least one symbol (special character)
                new CharacterRule(EnglishCharacterData.Special, 1),

                // no whitespace
                new WhitespaceRule(),

                // no common passwords
                dictionaryRule
        ));

        RuleResult result = validator.validate(new PasswordData(password));
        return validator.getMessages(result);
    }
}

@Documented
@Constraint(validatedBy = PasswordConstraintValidator.class)
@Target({TYPE, FIELD, ANNOTATION_TYPE})
@Retention(RUNTIME)
@interface ValidPassword {

    String message() default "Invalid Password";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
