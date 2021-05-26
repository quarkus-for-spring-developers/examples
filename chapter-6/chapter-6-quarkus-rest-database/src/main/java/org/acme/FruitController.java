package org.acme;

import org.acme.domain.Fruit;
import org.acme.exception.FruitsExceptions;
import org.acme.service.FruitRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Spliterator;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping(value = "/api/fruits")
public class FruitController {

    private final FruitRepository repository;

    public FruitController(FruitRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/{id}")
    public Fruit get(@PathVariable(value="manolo") Long id) {
        verifyFruitExists(id);

        return repository.findById(id).get();
    }

    @GetMapping
    public List<Fruit> getAll() {
        Spliterator<Fruit> fruits = repository.findAll()
                .spliterator();

        return StreamSupport
                .stream(fruits, false)
                .collect(Collectors.toList());
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Fruit post(@RequestBody(required = false) Fruit fruit) {
        verifyCorrectPayload(fruit);

        return repository.save(fruit);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}")
    public Fruit put(@PathVariable("id") Long id, @RequestBody(required = false) Fruit fruit) {
        verifyFruitExists(id);
        verifyCorrectPayload(fruit);

        fruit.setId(id);
        return repository.save(fruit);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        verifyFruitExists(id);

        repository.deleteById(id);
    }

    private void verifyFruitExists(Long id) {
        if (!repository.existsById(id)) {
            throw new FruitsExceptions.NotFoundException(String.format("Fruit with id=%d was not found", id));
        }
    }

    private void verifyCorrectPayload(Fruit fruit) {
        if (Objects.isNull(fruit)) {
            throw new FruitsExceptions.UnsupportedMediaTypeException("Fruit cannot be null");
        }

        if (Objects.isNull(fruit.getName()) || fruit.getName().trim().length() == 0) {
            throw new FruitsExceptions.UnprocessableEntityException("The name is required!");
        }

        if (!Objects.isNull(fruit.getId())) {
            throw new FruitsExceptions.UnprocessableEntityException("Id field must be generated");
        }
    }

}
