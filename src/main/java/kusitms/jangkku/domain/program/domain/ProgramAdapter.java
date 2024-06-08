package kusitms.jangkku.domain.program.domain;

import kusitms.jangkku.domain.program.dao.BrandingRepository;
import kusitms.jangkku.domain.program.domain.model.Branding;
import kusitms.jangkku.domain.program.exception.ProgramException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

import static kusitms.jangkku.domain.program.exception.ProgramErrorResult.NOT_FOUND_PROGRAM;

@Service
@RequiredArgsConstructor
public class ProgramAdapter {
    private final BrandingRepository brandingRepository;

    public Branding findBrandingById(Long id){
        return findFunction(() -> brandingRepository.findById(id));
    }

    private Branding findFunction(Supplier<Optional<Branding>> function){
        return function.get().orElseThrow(() -> new ProgramException(NOT_FOUND_PROGRAM));
    }
}