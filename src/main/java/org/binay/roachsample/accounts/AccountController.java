package org.binay.roachsample.accounts;

import org.binay.roachsample.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;

@RestController
@RequestMapping("api/account")
public class AccountController {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PagedResourcesAssembler<Account> pagedResourcesAssembler;

    @GetMapping()
    public Flux<Account> index() {
       return accountRepository.findAll();
    }
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Account> createTutorial(@RequestBody Account account) {
       return accountRepository.save(account);
    }

   /* @GetMapping
    public ResponseEntity<RepresentationModel> index() {
        RepresentationModel index = new RepresentationModel();

        index.add(linkTo(methodOn(AccountController.class)
                .listAccounts(PageRequest.of(0, 5)))
                .withRel("accounts"));

        index.add(linkTo(AccountController.class)
                .slash("transfer{?fromId,toId,amount}")
                .withRel("transfer"));

        return new ResponseEntity<>(index, HttpStatus.OK);
    }*/
/*
    @GetMapping("/account")
    @Transactional(propagation = REQUIRES_NEW)
    public HttpEntity<PagedModel<AccountModel>> listAccounts(
            @PageableDefault(size = 5, direction = Sort.Direction.ASC) Pageable page) {
        return ResponseEntity
                .ok(pagedResourcesAssembler.toModel(accountRepository.findAll(page), accountModelAssembler()));
    }

    @GetMapping(value = "/account/{id}")
    @Transactional(propagation = REQUIRES_NEW)
    public HttpEntity<AccountModel> getAccount(@PathVariable("id") Long accountId) {
        return new ResponseEntity<>(accountModelAssembler().toModel(accountRepository.getOne(accountId)),
                HttpStatus.OK);
    }*/

    @PostMapping(value = "/transfer")
    @Transactional(propagation = REQUIRES_NEW)
    public HttpEntity<BigDecimal> transfer(
            @RequestParam("fromId") Long fromId,
            @RequestParam("toId") Long toId,
            @RequestParam("amount") BigDecimal amount
    ) {
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Negative amount");
        }
        if (fromId.equals(toId)) {
            throw new IllegalArgumentException("From and to accounts must be different");
        }

        BigDecimal fromBalance = accountRepository.getBalance(fromId).add(amount.negate());

//        if (fromBalance.compareTo(BigDecimal.ZERO) < 0) {
//            throw new NegativeBalanceException("Insufficient funds " + amount + " for account " + fromId);
//        }

        accountRepository.updateBalance(fromId, amount.negate());
        accountRepository.updateBalance(toId, amount);

        return ResponseEntity.ok().build();
    }
/*
    private RepresentationModelAssembler<Account, AccountModel> accountModelAssembler() {
        return (entity) -> {
            AccountModel model = new AccountModel();
            model.setName(entity.getName());
            model.setType(entity.getType());
            model.setBalance(entity.getBalance());
            model.add(linkTo(methodOn(AccountController.class)
                    .getAccount(entity.getId())
            ).withRel(IanaLinkRelations.SELF));
            return model;
        };
    }*/
}
