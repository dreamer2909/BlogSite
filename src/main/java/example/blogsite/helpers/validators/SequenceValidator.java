package example.blogsite.helpers.validators;

import javax.validation.GroupSequence;
import javax.validation.groups.Default;

@GroupSequence({Default.class, SecondGroup.class})
public interface SequenceValidator {
}
