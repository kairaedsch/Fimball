package sep.fimball.model.physics.element;

import org.junit.Test;
import org.mockito.Mockito;
import sep.fimball.general.data.Vector2;
import sep.fimball.model.physics.collider.Collider;
import sep.fimball.model.physics.game.CollisionEventArgs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

/**
 * Tests für die Klasse PhysicsElement.
 */
public class PhysicsElementTest
{
    /**
     * Überprüft die Korrektheit der Methode {@link PhysicsElement#checkCollision}.
     */
    @Test
    public void checkCollision()
    {
        // Initialisiere PhysicsElement mit Mock
        BasePhysicsElement basePhysicsElement = Mockito.mock(BasePhysicsElement.class);
        Collider colliderA = Mockito.mock(Collider.class);
        Collider colliderB = Mockito.mock(Collider.class);
        when(colliderA.getId()).thenReturn(11111);
        when(colliderB.getId()).thenReturn(22222);
        List<Collider> colliders = new ArrayList<>();
        colliders.add(colliderA);
        colliders.add(colliderB);
        when(basePhysicsElement.getColliders()).thenReturn(colliders);
        PhysicsElement<Object> physicsElement = new PhysicsElement<>(null, null, 0, 1, basePhysicsElement);

        // Initialisiere Liste von CollisionEventArgs und ein BallPhysicsElement Mock
        List<CollisionEventArgs<Object>> eventArgsList = new ArrayList<>();
        BallPhysicsElement<Object> ballPhysicsElement = Mockito.mock(BallPhysicsElement.class);

        // Teste mit keiner Kollision
        when(colliderA.checkCollision(eq(ballPhysicsElement), any())).thenReturn(Optional.empty());
        when(colliderB.checkCollision(eq(ballPhysicsElement), any())).thenReturn(Optional.empty());
        physicsElement.checkCollision(eventArgsList, ballPhysicsElement);
        assertThat("Da es keine Kollision gab, muss die Liste leer sein", eventArgsList.size(), is(0));

        // Teste mit einer Kollision
        when(colliderA.checkCollision(eq(ballPhysicsElement), any())).thenReturn(Optional.empty());
        when(colliderB.checkCollision(eq(ballPhysicsElement), any())).thenReturn(Optional.of(0D));
        physicsElement.checkCollision(eventArgsList, ballPhysicsElement);
        assertThat("Da es eine Kollision gab, muss die Liste genau ein Element haben", eventArgsList.size(), is(1));
        assertThat("und es muss colliderB sein", eventArgsList.get(0).getColliderId(), is(22222));

        // Leere Kollisions Liste
        eventArgsList.clear();

        // Teste mit zwei Kollisionen
        when(colliderA.checkCollision(eq(ballPhysicsElement), any())).thenReturn(Optional.of(0.0));
        when(colliderB.checkCollision(eq(ballPhysicsElement), any())).thenReturn(Optional.of(0.0));
        physicsElement.checkCollision(eventArgsList, ballPhysicsElement);
        assertThat("Da es zwei Kollisionen gab, muss die Liste genau zwei Elemente haben", eventArgsList.size(), is(2));
    }

    /**
     * Überprüft die Korrektheit der Methode {@link PhysicsElement#hasChanged}.
     */
    @Test
    public void hasChanged()
    {
        // Initialisiere PhysicsElement mit Mock
        BasePhysicsElement basePhysicsElement = Mockito.mock(BasePhysicsElement.class);
        when(basePhysicsElement.getColliders()).thenReturn(Collections.emptyList());
        PhysicsElement physicsElement = new PhysicsElement<>(null, null, 0, 1, basePhysicsElement);

        // Ändere die Position
        physicsElement.setPosition(new Vector2(1, 1));
        assertThat("Das Element hat sich geändert", physicsElement.hasChanged(), is(true));

        // Setzte den Änderungsstatus zurück und ändere die Position
        physicsElement.resetChanged();
        physicsElement.setRotation(10);
        assertThat("Das Element hat sich geändert", physicsElement.hasChanged(), is(true));

        // Setzte den Änderungsstatus zurück
        physicsElement.resetChanged();
        assertThat("Das Element hat sich nicht geändert", physicsElement.hasChanged(), is(false));
    }
}